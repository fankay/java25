package com.kaishengit;

import com.kaishengit.dao.UserDao;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class ApplicationTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void test() {
        //ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Application.class);
        //UserDao userDao = (UserDao) applicationContext.getBean("userDao");
        userDao.save();

        /*BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("");
        dataSource.setUrl("");
        dataSource.setUsername();
        dataSource.setPassword();*/
    }
}
