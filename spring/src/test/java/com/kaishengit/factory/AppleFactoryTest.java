package com.kaishengit.factory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class AppleFactoryTest {

    @Test
    public void test() {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        Apple apple = (Apple) applicationContext.getBean("appleFactory");
        System.out.println(apple);
        apple.helloWorld();

       /* AppleFactory appleFactory = new AppleFactory();
        Apple apple = appleFactory.getApple();
        apple.helloWorld();*/


        /*Apple apple = new Apple();
        apple.helloWorld();*/
    }
}
