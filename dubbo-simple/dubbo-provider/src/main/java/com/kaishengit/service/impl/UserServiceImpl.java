package com.kaishengit.service.impl;

import com.kaishengit.service.UserService;

public class UserServiceImpl implements UserService {

    public String getUserName(Integer id) {
        if(id.equals(1)) {
            return "Rose";
        } else {
            return "Jack";
        }
    }
}
