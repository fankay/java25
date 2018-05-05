package com.kaishengit.spring.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private SendMail sendMail;

    public void reg() {
        System.out.println("1. 查询账号是否注册");
        System.out.println("2. 发送邮件");
        sendMail.sendMail("jack@google.com");
        System.out.println("3. 添加数据库记录");
    }
}
