package com.kaishengit.dao;

public class UserDao {

    public void init() {
        System.out.println("init method");
    }

    public void destroy() {
        System.out.println("destroy method");
    }

    public UserDao() {
        System.out.println("create UserDao Object...");
    }

    public void save() {
        System.out.println("UserDao save....");
    }

}
