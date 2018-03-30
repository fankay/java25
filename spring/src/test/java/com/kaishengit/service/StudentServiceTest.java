package com.kaishengit.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentServiceTest {

    @Test
    public void save() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("spring/ioc.xml");
        StudentService studentService = (StudentService) applicationContext.getBean("studentService");
        studentService.save();
       /* OtherServcie otherServcie = (OtherServcie) applicationContext.getBean("otherService");
        otherServcie.show();*/
    }
}
