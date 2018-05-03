package com.kaishengit.jedis;

import com.google.gson.Gson;
import com.kaishengit.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redis.xml")
public class JedisSpring {

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void setStringValue() {
        Jedis jedis = jedisPool.getResource();
        jedis.set("name","李思");
        jedis.close();
    }

    @Test
    public void getStringValue() {
        Jedis jedis = jedisPool.getResource();
        String name = jedis.get("name");
        System.out.println(name);
        jedis.close();
    }

    @Test
    public void saveUserToRedis() {
        Jedis jedis = jedisPool.getResource();

        User user = new User(1001,"Alex","北京");
        String json = new Gson().toJson(user);

        jedis.set("user:1001",json);

        jedis.close();
    }

    @Test
    public void getUserFromRedis() {
        Jedis jedis = jedisPool.getResource();

        String json = jedis.get("user:1001");

        User user = new Gson().fromJson(json,User.class);
        System.out.println(user);
        jedis.close();
    }

}
