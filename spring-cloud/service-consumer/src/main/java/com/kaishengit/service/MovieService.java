package com.kaishengit.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getMovieNameByIdFallBack",threadPoolProperties = {
            @HystrixProperty(name = "coreSize",value = "20")
    })
    public String getMovieNameById(Integer id) {
        String url = "http://MOVIE-SERVICE-PROVIDER/movie/{1}";
        return restTemplate.getForObject(url,String.class,id);
    }

    public String getMovieNameByIdFallBack(Integer id) {
        return "Null";
    }

}
