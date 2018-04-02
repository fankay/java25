package com.kaishengit.service;

import com.kaishengit.BaseTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends BaseTestCase {

    @Autowired
    private UserService userService;

    @Test
    public void save() {
        userService.save();
    }

}
