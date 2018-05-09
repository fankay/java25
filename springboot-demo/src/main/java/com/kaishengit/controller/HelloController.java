package com.kaishengit.controller;

import com.kaishengit.config.QiniuConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private QiniuConfig qiniuConfig;

    @GetMapping("/hello")
    public String hello() {
        System.out.println("AK:" + qiniuConfig.getAk() + " SK:" + qiniuConfig.getSk());
        return "Hello,SpringBoot";
    }
}
