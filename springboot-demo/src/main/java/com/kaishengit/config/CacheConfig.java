package com.kaishengit.config;

import com.kaishengit.prop.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class CacheConfig {

    @Autowired
    private RedisProperties redisProperties;

    /**
     * EhCache的缓存管理器
     */
    /*@Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager();
    }*/

    /**
     * Redis CacheManager
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        //将redis的键添加前缀，防止命名冲突
        redisCacheManager.setUsePrefix(true);

       /* Map<String, Long> expires = new HashMap<>();
        expires.put("movie",10L);*/

        redisCacheManager.setExpires(redisProperties.getExpires());
        return redisCacheManager;
    }

}
