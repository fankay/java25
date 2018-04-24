package com.kaishengit;

import com.kaishengit.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dubbo.xml");
        UserService userService = (UserService) applicationContext.getBean("userService");
        String userName = userService.getUserName(1);
        System.out.println("userName:" + userName);

        System.in.read();
    }
}
