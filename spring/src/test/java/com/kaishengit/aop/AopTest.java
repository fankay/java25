package com.kaishengit.aop;

import com.kaishengit.proxy.Mp3;
import com.kaishengit.proxy.Player;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopTest {

    @Test
    public void test() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/aop.xml");
        Player mp3 = (Player) applicationContext.getBean("mp3");
        System.out.println(mp3.getClass().getName());

        mp3.play("Hello");
        System.out.println("----------------------");
        mp3.stop();
    }
}
