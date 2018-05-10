package com.kaishengit.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(String username,String address,String password) {
        String sql = "insert into t_user(user_name,address,password) values(?,?,?)";
        jdbcTemplate.update(sql,username,address,password);
    }

}
