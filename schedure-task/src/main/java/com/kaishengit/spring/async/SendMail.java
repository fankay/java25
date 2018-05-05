package com.kaishengit.spring.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SendMail {

    @Async
    public void sendMail(String email)  {
        System.out.println("Begin send mail to :" + email);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(1==1) {
            throw new RuntimeException("发送电子邮件异常：" + email);
        }
        System.out.println("End send mail to :" + email);
    }
}
