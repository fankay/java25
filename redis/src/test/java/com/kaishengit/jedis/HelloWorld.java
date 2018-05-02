package com.kaishengit.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

public class HelloWorld {

    @Test
    public void hello() {
        //如果要连接远程的redis，需要修改redis的配置文件 bind=指定ip或0.0.0.0(生成环境勿用)
        Jedis jedis = new Jedis("192.168.135.173",6379);
        String pong = jedis.ping();
        System.out.println(pong);
        jedis.close();
    }

    @Test
    public void setString() {
        Jedis jedis = new Jedis("192.168.135.173",6379);
        jedis.set("name:1","Alex");

        String name = jedis.get("name:1");
        Assert.assertEquals(name,"Alex");

        jedis.close();
    }

    @Test
    public void lpush() {
        Jedis jedis = new Jedis("192.168.135.173",6379);
        jedis.lpush("user:1:address","beijing","shanghai","hangzhou");
        jedis.close();
    }

    @Test
    public void lrange() {
        Jedis jedis = new Jedis("192.168.135.173",6379);
        List<String> result = jedis.lrange("user:1:address",0,-1);
        for(String str  :result) {
            System.out.println(str);
        }
        jedis.close();
    }

    @Test
    public void zadd() {
        Jedis jedis = new Jedis("192.168.135.173",6379);
        jedis.zadd("class:1001",89,"tom");
        double score = jedis.zscore("class:1001","tom");
        System.out.println(score);
        jedis.close();
    }

    @Test
    public void pool() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMinIdle(5);

        JedisPool jedisPool = new JedisPool(poolConfig,"192.168.135.173",6379);
        Jedis jedis = jedisPool.getResource();

        jedis.del("name:1");

        jedis.close();
        jedisPool.destroy();

    }




}
