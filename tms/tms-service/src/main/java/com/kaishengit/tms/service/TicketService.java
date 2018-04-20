package com.kaishengit.tms.service;

import com.kaishengit.tms.entity.TicketInRecord;

/**
 * 年票业务类
 * @author fankay
 */
public interface TicketService {
    /**
     * 保存一个入库记录
     * @param ticketInRecord
     */
    void saveTicketInRecord(TicketInRecord ticketInRecord);
}
