package com.kaishengit.tms.service.impl;

import com.kaishengit.tms.entity.StoreAccount;
import com.kaishengit.tms.entity.TicketStore;
import com.kaishengit.tms.mapper.StoreAccountMapper;
import com.kaishengit.tms.mapper.TicketStoreMapper;
import com.kaishengit.tms.service.TicketStoreService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 年票售票点业务层
 * @author fankay
 */
@Service
public class TicketStoreServiceImpl implements TicketStoreService {


    @Autowired
    private TicketStoreMapper ticketStoreMapper;
    @Autowired
    private StoreAccountMapper storeAccountMapper;

    /**
     * 创建新的售票点
     * @param ticketStore
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveNewTicktStore(TicketStore ticketStore) {
        ticketStore.setCreateTime(new Date());
        ticketStoreMapper.insertSelective(ticketStore);

        //创建售票点账号
        StoreAccount storeAccount = new StoreAccount();
        storeAccount.setStoreAccount(ticketStore.getStoreTel());
        //默认密码为手机号码后六位
        storeAccount.setStorePassword(DigestUtils.md5Hex(ticketStore.getStoreTel().substring(5)));
        storeAccount.setCreateTime(new Date());
        storeAccount.setTicketStoreId(ticketStore.getId());
        storeAccount.setStoreState(StoreAccount.ACCOUNT_STATE_NORMAL);

        storeAccountMapper.insertSelective(storeAccount);

        //更新关联的账号ID
        ticketStore.setStoreAccountId(storeAccount.getId());
        ticketStoreMapper.updateByPrimaryKeySelective(ticketStore);
    }
}
