package com.kaishengit.cache;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisCacheHelper {
    /*private RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisTemplate.setValueSerializer(new StringRedisSerializer());
    }

    public Object get(String key,Class clazz) {
        if(redisTemplate.hasKey(key)) {
            String json = (String) redisTemplate.opsForValue().get(key);
            return new Gson().fromJson(json,clazz);
        }
        return null;
    }

    public void set(String key, Object o) {
        String json = new Gson().toJson(o);
        redisTemplate.opsForValue().set(key,json);
    }

    public void set(String key, Object o,long expires) {
        String json = new Gson().toJson(o);
        redisTemplate.opsForValue().set(key,json,expires,TimeUnit.SECONDS);
    }

    public void remove(String key) {
        redisTemplate.delete(key);
    }*/
}
