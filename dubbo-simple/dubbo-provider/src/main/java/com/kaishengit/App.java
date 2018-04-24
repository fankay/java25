package com.kaishengit;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dubbo.xml");
        applicationContext.start();

        System.out.println("服务已启动完毕-------");

        System.in.read();
    }
}
