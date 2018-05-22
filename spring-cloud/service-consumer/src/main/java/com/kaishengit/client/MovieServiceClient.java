package com.kaishengit.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "API-GATEWAY")
public interface MovieServiceClient {

    @GetMapping("/api/v1/movie/{id}?accessToken=1001")
    String getMovieName(@PathVariable(name = "id") Integer id);

    @PostMapping("/api/v1//movie/new")
    String saveNewMovie(@RequestParam(name = "movieName") String movieName,
                        @RequestParam(name = "author") String author);
}
