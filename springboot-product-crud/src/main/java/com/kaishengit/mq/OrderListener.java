package com.kaishengit.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

    @JmsListener(destination = "user-order")
    public void orderListener(String id) {
        System.out.println("同步订单到数据库：" + id);
    }

}
