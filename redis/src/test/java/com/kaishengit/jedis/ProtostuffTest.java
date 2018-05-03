package com.kaishengit.jedis;

import com.kaishengit.entity.User;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redis.xml")
public class ProtostuffTest {

    @Autowired
    private JedisPool jedisPool;


    @Test
    public void saveUserToRedis() {
        Jedis jedis = jedisPool.getResource();

        User user = new User(1004,"王五","北京");

        Schema<User> userSchema = RuntimeSchema.getSchema(User.class);
        byte[] bytes = ProtobufIOUtil.toByteArray(user,userSchema,LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));

        jedis.set("user:1004".getBytes(),bytes);
        jedis.close();
    }

    @Test
    public void getUserFromRedis() {
        Jedis jedis = jedisPool.getResource();

        byte[] bytes = jedis.get("user:1004".getBytes());
        Schema<User> userSchema = RuntimeSchema.getSchema(User.class);
        User user = new User();
        ProtobufIOUtil.mergeFrom(bytes,user,userSchema);

        System.out.println(user);

        jedis.close();
    }
}
