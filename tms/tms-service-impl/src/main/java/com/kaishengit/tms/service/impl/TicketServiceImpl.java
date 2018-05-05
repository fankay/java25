package com.kaishengit.tms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kaishengit.tms.entity.Account;
import com.kaishengit.tms.entity.Customer;
import com.kaishengit.tms.entity.CustomerExample;
import com.kaishengit.tms.entity.Ticket;
import com.kaishengit.tms.entity.TicketExample;
import com.kaishengit.tms.entity.TicketInRecord;
import com.kaishengit.tms.entity.TicketInRecordExample;
import com.kaishengit.tms.entity.TicketOrder;
import com.kaishengit.tms.entity.TicketOutRecord;
import com.kaishengit.tms.entity.TicketOutRecordExample;
import com.kaishengit.tms.entity.TicketStore;
import com.kaishengit.tms.exception.ServiceException;
import com.kaishengit.tms.mapper.CustomerMapper;
import com.kaishengit.tms.mapper.TicketInRecordMapper;
import com.kaishengit.tms.mapper.TicketMapper;
import com.kaishengit.tms.mapper.TicketOrderMapper;
import com.kaishengit.tms.mapper.TicketOutRecordMapper;
import com.kaishengit.tms.mapper.TicketStoreMapper;
import com.kaishengit.tms.service.TicketService;
import com.kaishengit.tms.util.SnowFlake;
import com.kaishengit.tms.util.shiro.ShiroUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 年票业务类
 * @author fankay
 */
@Service
public class TicketServiceImpl implements TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Value("${snowFlake.dataCenterId}")
    private Integer snowFlakeDataCenter;

    @Value("${snowFlake.machineId}")
    private Integer snowFlakeMachineId;

    @Autowired
    private ShiroUtil shiroUtil;

    @Autowired
    private TicketInRecordMapper ticketInRecordMapper;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private TicketOutRecordMapper ticketOutRecordMapper;

    @Autowired
    private TicketStoreMapper ticketStoreMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private TicketOrderMapper ticketOrderMapper;

    /**
     * 保存一个入库记录
     *
     * @param ticketInRecord
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveTicketInRecord(TicketInRecord ticketInRecord) throws ServiceException {
        BigInteger start = new BigInteger(ticketInRecord.getBeginTicketNum());
        BigInteger end = new BigInteger(ticketInRecord.getEndTicketNum());
        if(start.compareTo(end) >= 0) {
            throw new ServiceException("起始票号必须小于截至票号");
        }

        //判断当前的入库票号的范围是否和之前入库的范围重合，如果重回则不能添加
        List<TicketInRecord> ticketInRecordList = ticketInRecordMapper.selectByExample(new TicketInRecordExample());
        for(TicketInRecord record : ticketInRecordList) {
            BigInteger recordStart = new BigInteger(record.getBeginTicketNum());
            BigInteger recordEnd = new BigInteger(record.getEndTicketNum());
            boolean flag = (recordStart.compareTo(start) <= 0 && recordEnd.compareTo(start) >= 0) || (recordStart.compareTo(end) <= 0 && recordEnd.compareTo(end) >= 0);
            if(flag) {
                throw new ServiceException("票号区间重复，添加失败");
            }
        }

        //设置入库时间
        ticketInRecord.setCreateTime(new Date());
        //获取总数量=截至票号-起始票号+1
        BigInteger totalNUm = end.subtract(start).add(new BigInteger(String.valueOf(1)));
        ticketInRecord.setTotalNum(totalNUm.intValue());

        //获取当前登录的对象
        Account account = shiroUtil.getCurrentAccount();
        ticketInRecord.setAccountId(account.getId());
        ticketInRecord.setAccountName(account.getAccountName());

        //设置入库的内容
        ticketInRecord.setContent(ticketInRecord.getBeginTicketNum()+"-"+ticketInRecord.getEndTicketNum());

        ticketInRecordMapper.insertSelective(ticketInRecord);

        logger.info("新增年票入库： {} 入库人：{}",ticketInRecord,account);

        //添加totalNum条年票记录
        List<Ticket> ticketList = new ArrayList<>();
        for(int i = 0;i < totalNUm.intValue();i++) {
            Ticket  ticket = new Ticket();
            ticket.setCreateTime(new Date());
            ticket.setTicketInTime(new Date());
            ticket.setTicketNum(start.add(new BigInteger(String.valueOf(i))).toString());
            ticket.setTicketState(Ticket.TICKET_STATE_IN_STORE);
            ticketList.add(ticket);
        }

        //批量保存年票记录
        ticketMapper.batchInsert(ticketList);
    }

    /**
     * 根据当前页号查询入库记录列表
     *
     * @param pageNo
     * @return
     */
    @Override
    public PageInfo<TicketInRecord> findTicketRecordByPageNo(Integer pageNo) {
        PageHelper.startPage(pageNo,15);

        TicketInRecordExample inRecordExample = new TicketInRecordExample();
        inRecordExample.setOrderByClause("id desc");

        List<TicketInRecord> ticketInRecordList = ticketInRecordMapper.selectByExample(inRecordExample);
        return new PageInfo<>(ticketInRecordList);
    }

    /**
     * 根据ID删除年票入库记录
     * @param id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delInRecordById(Integer id) {
        //查找对应的年票记录
        TicketInRecord inRecord = ticketInRecordMapper.selectByPrimaryKey(id);
        if(inRecord != null) {
            //查找该记录对应的年票
            List<Ticket> ticketList = ticketMapper.findByBeginNumAndEndNumAndState(inRecord.getBeginTicketNum(),inRecord.getEndTicketNum(),Ticket.TICKET_STATE_IN_STORE);

            //判断年票数量和入库记录总数量是否相同，如果不同，则表示有的年票状态已经发生修改，不能删除
            if(!inRecord.getTotalNum().equals(ticketList.size())) {
                throw new ServiceException("该批次年票状态已经发生改变，不能删除");
            }
            //删除年票
            List<Long> idList = Lists.newArrayList(Collections2.transform(ticketList, new Function<Ticket, Long>() {
                @Nullable
                @Override
                public Long apply(@Nullable Ticket f) {
                    return f.getId();
                }
            }));
            ticketMapper.batchDeleteById(idList);
            //删除年票入库记录
            ticketInRecordMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 统计各个状态的年票数量
     *
     * @return
     */
    @Override
    public Map<String, Long> countTicketByState() {
        return ticketMapper.countByState();
    }

    /**
     * 保存新的年票下发记录
     *
     * @param ticketOutRecord 下发记录对象
     * @throws ServiceException 保存失败时抛出的异常
     */
    @Override
    public void saveTicketOutRecord(TicketOutRecord ticketOutRecord) throws ServiceException {
        //判断当前票段内是否有非[已入库]状态的票，如果有则不能下发
        List<Ticket> ticketList = ticketMapper.findByBeginNumAndEndNum(ticketOutRecord.getBeginTicketNum(),ticketOutRecord.getEndTicketNum());

        for(Ticket ticket : ticketList) {
            if(!Ticket.TICKET_STATE_IN_STORE.equals(ticket.getTicketState())) {
                throw new ServiceException("该票段内有已经下发的票，请重新选择");
            }
        }

        //获取当前下发的售票点对象，并赋值售票点名称
        TicketStore ticketStore = ticketStoreMapper.selectByPrimaryKey(ticketOutRecord.getStoreAccountId());
        ticketOutRecord.setStoreAccountName(ticketStore.getStoreName());
        //选择的总数量
        int totalSize = ticketList.size();
        //总价格
        BigDecimal totalPrice = ticketOutRecord.getPrice().multiply(new BigDecimal(totalSize));
        //当前登录的对象
        Account account = shiroUtil.getCurrentAccount();

        ticketOutRecord.setCreateTime(new Date());
        ticketOutRecord.setContent(ticketOutRecord.getBeginTicketNum() + " - " + ticketOutRecord.getEndTicketNum());
        ticketOutRecord.setOutAccountId(account.getId());
        ticketOutRecord.setOutAccountName(account.getAccountName());
        ticketOutRecord.setTotalNum(totalSize);
        ticketOutRecord.setState(TicketOutRecord.STATE_NO_PAY);
        ticketOutRecord.setTotalPrice(totalPrice);

        ticketOutRecordMapper.insertSelective(ticketOutRecord);

        logger.info("新增年票下发记录：{}",ticketOutRecord);
    }

    /**
     * 根据当前页号查询所有的下发记录
     *
     * @param pageNo
     * @return
     */
    @Override
    public PageInfo<TicketOutRecord> findTicketOutRecordByPageNo(Integer pageNo) {
        return findTicketOutRecordByPageNoAndQueryParam(pageNo,Maps.newHashMap());
    }

    /**
     * 根据主键删除下发单
     *
     * @param id
     */
    @Override
    public void delOutRecordById(Integer id) {
        TicketOutRecord record = ticketOutRecordMapper.selectByPrimaryKey(id);
        if(record != null) {
            //只有未支付的才可以删除
            if(TicketOutRecord.STATE_NO_PAY.equals(record.getState())) {
                ticketOutRecordMapper.deleteByPrimaryKey(id);
            }
        }
    }

    /**
     * 根据当前页号和查询参数查询下发列表
     *
     * @param pageNo     当前页号
     * @param queryParam 查询参数集合
     * @return
     */
    @Override
    public PageInfo<TicketOutRecord> findTicketOutRecordByPageNoAndQueryParam(Integer pageNo, Map<String, Object> queryParam) {
        PageHelper.startPage(pageNo,15);

        TicketOutRecordExample ticketOutRecordExample = new TicketOutRecordExample();
        TicketOutRecordExample.Criteria criteria = ticketOutRecordExample.createCriteria();

        String state = (String) queryParam.get("state");
        if(StringUtils.isNotEmpty(state)) {
            criteria.andStateEqualTo(state);
        }
        ticketOutRecordExample.setOrderByClause("id desc");

        List<TicketOutRecord> ticketOutRecordList = ticketOutRecordMapper.selectByExample(ticketOutRecordExample);
        return new PageInfo<>(ticketOutRecordList);
    }

    /**
     * 根据ID对对应的下发订单进行支付-财务结算
     *
     * @param id
     * @param payType
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void payTicketOutRecord(Integer id, String payType) {
        TicketOutRecord ticketOutRecord = ticketOutRecordMapper.selectByPrimaryKey(id);
        if(ticketOutRecord != null && TicketOutRecord.STATE_NO_PAY.equals(ticketOutRecord.getState())) {
            ticketOutRecord.setPayType(payType);

            Account account = shiroUtil.getCurrentAccount();
            ticketOutRecord.setFinanceAccountId(account.getId());
            ticketOutRecord.setFinanceAccountName(account.getAccountName());
            ticketOutRecord.setState(TicketOutRecord.STATE_PAY);

            //修改下发记录
            ticketOutRecordMapper.updateByPrimaryKeySelective(ticketOutRecord);

            //将对应的年票状态修改为【已下发】
            List<Ticket> ticketList = ticketMapper.findByBeginNumAndEndNum(ticketOutRecord.getBeginTicketNum(),ticketOutRecord.getEndTicketNum());
            for (Ticket ticket : ticketList) {
                ticket.setTicketState(Ticket.TICKET_STATE_OUT_STORE);
                ticket.setStoreAccountId(ticketOutRecord.getStoreAccountId());
                ticket.setTicketOutTime(DateTime.now().toString("YYYY-MM-dd"));
                ticket.setUpdateTime(new Date());

                ticketMapper.updateByPrimaryKeySelective(ticket);
            }
        }
    }

    /**
     * 根据主键查找对应的下发单
     *
     * @param id
     * @return
     */
    @Override
    public TicketOutRecord findTicketOutRecordById(Integer id) {
        return ticketOutRecordMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据售票点ID查询年票数量
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Long> countTicketByStateAndStoreAccountId(Integer id) {
        return ticketMapper.countByStateAndStoreAccountId(id);
    }

    /**
     * 销售年票
     * @param customer 销售年票的客户对象
     * @param ticketNum 年票票号
     * @param ticketStore 当前售票点
     * @param price 销售价格
     * @throws ServiceException 销售失败抛出异常
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void salesTicket(Customer customer, String ticketNum, TicketStore ticketStore, BigDecimal price) throws ServiceException {
        //1. 根据年票票号查找年票，查看年票是否属于该售票点并看年票的状态是否为【已下发】
        TicketExample ticketExample = new TicketExample();
        ticketExample.createCriteria().andTicketNumEqualTo(ticketNum);

        List<Ticket> ticketList = ticketMapper.selectByExample(ticketExample);
        if(ticketList != null && !ticketList.isEmpty()) {
            Ticket ticket = ticketList.get(0);
            if(Ticket.TICKET_STATE_OUT_STORE.equals(ticket.getTicketState())) {
                if(ticket.getStoreAccountId().equals(ticketStore.getId())) {

                    //判断该用户是否办理过年票
                    CustomerExample customerExample = new CustomerExample();
                    customerExample.createCriteria().andCustomerIdCardEqualTo(customer.getCustomerIdCard());
                    List<Customer> customerList = customerMapper.selectByExample(customerExample);
                    if(customerList != null && !customerList.isEmpty()) {
                        Customer dbCustomer = customerList.get(0);
                        //查询用户当前绑定的年票
                        Ticket customerTicket = ticketMapper.selectByPrimaryKey(dbCustomer.getTicketId());
                        if(customerTicket != null) {
                            if(Ticket.TICKET_STATE_SALE.equals(customerTicket.getTicketState())) {
                                throw new ServiceException("该用户已购买过年票，不能再次购买");
                            }
                        } else {
                            //用户存在，但未绑定年票
                            customer = dbCustomer;
                        }
                    } else {
                        //2保存客户
                        customer.setTicketId(ticket.getId());
                        customer.setCreateTime(new Date());
                        customerMapper.insertSelective(customer);
                    }


                    //3. 将该年票状态修改为[已销售]
                    ticket.setTicketState(Ticket.TICKET_STATE_SALE);
                    //设置年票有效期
                    ticket.setTicketValidityStart(new Date());

                    DateTime endDate = DateTime.now().plusYears(1);
                    ticket.setTicketValidityEnd(endDate.toDate());

                    //绑定销售用户
                    ticket.setCustomerId(customer.getId());
                    //修改年票对象
                    ticketMapper.updateByPrimaryKeySelective(ticket);

                    //4. 创建销售订单
                    TicketOrder ticketOrder = new TicketOrder();
                    ticketOrder.setCreateTime(new Date());
                    ticketOrder.setCustomerId(customer.getId());
                    ticketOrder.setStoreAccountId(ticketStore.getId());
                    ticketOrder.setTicketId(ticket.getId());
                    ticketOrder.setTicketOrderPrice(price);
                    //流水号
                    SnowFlake snowFlake = new SnowFlake(snowFlakeDataCenter,snowFlakeMachineId);
                    ticketOrder.setTicketOrderNum(String.valueOf(snowFlake.nextId()));
                    ticketOrder.setTicketOrderType(TicketOrder.ORDER_TYPE_NEW);

                    ticketOrderMapper.insertSelective(ticketOrder);



                } else {
                    throw new ServiceException("该年票不属于当前售票点，请核查");
                }
            } else {
                throw new ServiceException("该年票状态异常，请核查");
            }
        } else {
            throw new ServiceException("该年票不存在，请核查票号");
        }



    }

    /**
     * 根据年票号码查询年票
     *
     * @param ticketNum
     * @return
     */
    @Override
    public Ticket findTicketByTicketNum(String ticketNum) {
        TicketExample ticketExample = new TicketExample();
        ticketExample.createCriteria().andTicketNumEqualTo(ticketNum);

        List<Ticket> ticketList = ticketMapper.selectByExample(ticketExample);
        return (ticketList == null || ticketList.isEmpty()) ? null : ticketList.get(0);
    }

    /**
     * 根据年票的状态查询年票列表
     *
     * @param ticketState 年票状态
     * @return
     */
    @Override
    public List<Ticket> findTicketByState(String ticketState) {
        TicketExample ticketExample = new TicketExample();
        ticketExample.createCriteria().andTicketStateEqualTo(ticketState);
        return ticketMapper.selectByExample(ticketExample);
    }

    /**
     * 批量修改年票的状态
     *
     * @param outTimeTicketList 被修改的年票集合
     */
    @Override
    public void batchUpdateTicketState(List<Ticket> outTimeTicketList) {
        ticketMapper.batchUpdateState(outTimeTicketList,Ticket.TICKET_STATE_OUT_DATE);
    }
}
