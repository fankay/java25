package com.kaishengit.dao;

import javax.inject.Named;

@Named
public class UserDao {

    public void save() {
        System.out.println("userDao save...");
    }
}
