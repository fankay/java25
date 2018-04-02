package com.kaishengit.proxy;

public class SalesProxy extends Sales {

    @Override
    public void sale() {
        System.out.println("售前咨询...");
        super.sale();
        System.out.println("售后服务....");
    }
}
