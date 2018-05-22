package com.kaishengit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
public class PayController {

    @GetMapping("/alipay")
    public String alipay(String payAccount,double money) {
        return payAccount + " alipay success :" + money;
    }

    @GetMapping("/weixin")
    public String weixin(String payAccount,double money) {
        return payAccount + " weixin pay success :" + money;
    }
}
