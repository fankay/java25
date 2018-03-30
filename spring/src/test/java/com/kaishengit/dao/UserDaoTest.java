package com.kaishengit.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.IOException;

public class UserDaoTest {

    @Test
    public void saveTest() throws IOException {
        //1.获取Spring容器
        ClassPathXmlApplicationContext applicationContext =
                //new FileSystemXmlApplicationContext("D:/applicationContext.xml");
                new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        //2.从容器中获取UserDao对象
       UserDao userDao = (UserDao) applicationContext.getBean("userDao");
        //UserDao userDao2 = (UserDao) applicationContext.getBean("userDao");

        userDao.save();

        //3.销毁容器
        //applicationContext.destroy();

    }

}
