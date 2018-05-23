package com.kaishengit.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
@RefreshScope
public class PayController {

    @Value("${tax}")
    private double tax;

    @GetMapping("/alipay")
    public String alipay(String payAccount,double money) {
        double payTax = money * tax;
        return payAccount + " alipay success :" + money + "  tax:" + payTax;
    }

    @GetMapping("/weixin")
    public String weixin(String payAccount,double money) {
        double payTax = money * tax;
        return payAccount + " weixin pay success :" + money + "  tax:" + payTax;
    }
}
