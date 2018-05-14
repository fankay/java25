package com.kaishengit.job;

import com.kaishengit.entity.Product;
import com.kaishengit.mapper.ProductMapper;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

public class SyncInventoryToDataBaseJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取商品的ID
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        Integer productId = dataMap.getIntegerFromString("productId");

        //获取ApplicationContext
        try {
            ApplicationContext applicationContext = (ApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContext");
            //获取StringRedisTemplate
            StringRedisTemplate redisTemplate = applicationContext.getBean(StringRedisTemplate.class);
            //获取当前库存的数量(Redis)
            Long size = redisTemplate.opsForList().size("product:"+productId+":inventory");


            //获取ProductMapper
            ProductMapper productMapper = applicationContext.getBean(ProductMapper.class);
            Product product = productMapper.selectByPrimaryKey(productId);
            product.setProductInventory(size.intValue());

            productMapper.updateByPrimaryKeySelective(product);
            System.out.println("同步数据库完成:" + productId);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }


    }
}
