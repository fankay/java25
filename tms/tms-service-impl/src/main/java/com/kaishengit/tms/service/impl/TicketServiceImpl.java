package com.kaishengit.tms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.tms.entity.Account;
import com.kaishengit.tms.entity.Ticket;
import com.kaishengit.tms.entity.TicketExample;
import com.kaishengit.tms.entity.TicketInRecord;
import com.kaishengit.tms.entity.TicketInRecordExample;
import com.kaishengit.tms.exception.ServiceException;
import com.kaishengit.tms.mapper.TicketInRecordMapper;
import com.kaishengit.tms.mapper.TicketMapper;
import com.kaishengit.tms.service.TicketService;
import com.kaishengit.tms.util.shiro.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 年票业务类
 * @author fankay
 */
@Service
public class TicketServiceImpl implements TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private ShiroUtil shiroUtil;

    @Autowired
    private TicketInRecordMapper ticketInRecordMapper;
    @Autowired
    private TicketMapper ticketMapper;

    /**
     * 保存一个入库记录
     *
     * @param ticketInRecord
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveTicketInRecord(TicketInRecord ticketInRecord) {
        //设置入库时间
        ticketInRecord.setCreateTime(new Date());
        //获取总数量=截至票号-起始票号+1
        BigInteger start = new BigInteger(ticketInRecord.getBeginTicketNum());
        BigInteger end = new BigInteger(ticketInRecord.getEndTicketNum());

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
            TicketExample ticketExample = new TicketExample();
            ticketExample.createCriteria()
                    .andTicketNumGreaterThanOrEqualTo(inRecord.getBeginTicketNum())
                    .andTicketNumLessThanOrEqualTo(inRecord.getEndTicketNum())
                    .andTicketStateEqualTo(Ticket.TICKET_STATE_IN_STORE);
            List<Ticket> ticketList = ticketMapper.selectByExample(ticketExample);

            //判断年票数量和入库记录总数量是否相同，如果不同，则表示有的年票状态已经发生修改，不能删除
            if(!inRecord.getTotalNum().equals(ticketList.size())) {
                throw new ServiceException("该批次年票状态已经发生改变，不能删除");
            }
            //删除年票
            ticketMapper.deleteByExample(ticketExample);
            //删除年票入库记录
            ticketInRecordMapper.deleteByPrimaryKey(id);
        }
    }
}
