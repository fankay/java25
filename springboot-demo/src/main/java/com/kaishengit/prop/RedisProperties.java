package com.kaishengit.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {

    private Map<String, Long> expires;

    public void setExpires(Map<String, Long> expires) {
        this.expires = expires;
    }

    public Map<String, Long> getExpires() {
        return expires;
    }
}
