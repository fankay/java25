package com.kaishengit.spring.async;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-async.xml")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void reg() throws IOException {
        userService.reg();

        System.in.read();
    }

}