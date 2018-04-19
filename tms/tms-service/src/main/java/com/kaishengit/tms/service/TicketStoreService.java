package com.kaishengit.tms.service;

import com.kaishengit.tms.entity.TicketStore;

/**
 * 年票售票点业务类
 * @author fankay
 */
public interface TicketStoreService {
    /**
     * 创建新的售票点
     * @param ticketStore
     */
    void saveNewTicktStore(TicketStore ticketStore);
}
