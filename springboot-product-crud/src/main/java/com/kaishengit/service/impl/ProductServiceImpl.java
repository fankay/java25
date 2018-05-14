package com.kaishengit.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Product;
import com.kaishengit.entity.ProductExample;
import com.kaishengit.job.SyncInventoryToDataBaseJob;
import com.kaishengit.mapper.ProductMapper;
import com.kaishengit.service.ProductService;
import org.apache.activemq.command.ActiveMQQueue;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Random;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public PageInfo<Product> findByPageNo(Integer pageNo) {
        PageHelper.startPage(pageNo,10);
        return new PageInfo<>(productMapper.selectByExample(new ProductExample()));
    }

    @Override
    public void saveNewProduct(Product product) {
        productMapper.insertSelective(product);
        //将Product对象和库存放入redis中
        redisTemplate.opsForValue().set("product:"+product.getId(),JSON.toJSONString(product));

        for(int i= 1;i<= product.getProductInventory();i++) {
            redisTemplate.opsForList().leftPush("product:"+product.getId()+":inventory",String.valueOf(i));
        }
        //redisTemplate.opsForValue().set("product:"+product.getId()+":inventory",String.valueOf(product.getProductInventory()));

        //添加动态的定时任务，让秒杀结束后同步库存到数据库中
        addSyncInventoryJob(product.getId(),product.getEndTime());
    }

    private void addSyncInventoryJob(Integer id,String endTime) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAsString("productId",id);

        JobDetail jobDetail = JobBuilder.newJob(SyncInventoryToDataBaseJob.class)
                .withIdentity("product:"+id,"product:sync:database")
                .setJobData(jobDataMap)
                .build();

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        DateTime dateTime = dateTimeFormatter.parseDateTime(endTime);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0 ");
        stringBuilder.append(dateTime.getMinuteOfHour()).append(" ");
        stringBuilder.append(dateTime.getHourOfDay()).append(" ");
        stringBuilder.append(dateTime.getDayOfMonth()).append(" ");
        stringBuilder.append(dateTime.getMonthOfYear()).append(" ");
        stringBuilder.append("? ");
        stringBuilder.append(dateTime.getYear());

        String cron = stringBuilder.toString();

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        Trigger trigger = TriggerBuilder.newTrigger().withSchedule(cronScheduleBuilder).build();

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Product findById(Integer id) {
        String json = redisTemplate.opsForValue().get("product:"+id);
        Product product = JSON.parseObject(json,Product.class);

        //获取最新的库存
        Long size = redisTemplate.opsForList().size("product:"+id+":inventory");
        product.setProductInventory(size.intValue());

        return product;
    }

    @Override
    public void editProduct(Product product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public void deleteById(Integer id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void buyProduct(Integer id) throws RuntimeException {
        String json = redisTemplate.opsForValue().get("product:"+id);
        Product product = JSON.parseObject(json,Product.class);

        if(!product.isStart()) {
            throw new RuntimeException("你来早了，还没有开始");
        }
        if(product.isEnd()) {
            throw  new RuntimeException("抢购已结束");
        }

        //获取redis中的库存
        //int inventory = Integer.valueOf(redisTemplate.opsForValue().get("product:"+id+":inventory"));

/*        try {
            Thread.sleep(new Random().nextInt(200));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //list push pop
        if (redisTemplate.opsForList().leftPop("product:" + id + ":inventory") != null) {
            Long size = redisTemplate.opsForList().size("product:" + id + ":inventory");
            System.out.println("购买成功....." + size);
            //下订单-> MQ
            sendOrderInfoToMQ(id);
            //同步库存 -> 秒杀结束 -> Quartz  √
        } else {
            System.out.println("已售罄......");
        }

        //第一种解决方案
       /* if(redisTemplate.opsForValue().get("product:"+id+":inventory").equals("0")) {
            System.out.println("已售罄......");
        } else {
            Long size = redisTemplate.opsForValue().increment("product:"+id+":inventory",-1);
            System.out.println("购买成功....." + size);
        }*/




        /*if(product.getProductInventory() == 0) {
            throw  new RuntimeException("已售罄");
        }*/
        //同一个账号只能购买一件商品
        //过滤异常IP
        //！！！！不要超卖
        //减库存
        /*product.setProductInventory(product.getProductInventory() - 1);
        productMapper.updateByPrimaryKeySelective(product);
        System.out.println("购买成功....................................");*/
    }

    private void sendOrderInfoToMQ(Integer id) {
        //写redis product:order:101:23  -> 201805140909909(雪花算法)

        ActiveMQQueue queue = new ActiveMQQueue("user-order");
        jmsTemplate.send(queue, session -> {
            TextMessage textMessage = session.createTextMessage(String.valueOf(id));
            return textMessage;
        });
    }
}
