package com.kaishengit.dao;

import com.kaishengit.BaseTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDaoTest extends BaseTestCase {

    @Autowired
    private UserDao userDao;

    @Test
    public void save() {
        userDao.save();
    }

}
