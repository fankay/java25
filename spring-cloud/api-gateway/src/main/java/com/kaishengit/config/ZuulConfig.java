package com.kaishengit.config;

import com.kaishengit.filter.AccessFilter;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableZuulProxy
public class ZuulConfig {

    @Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }
}
