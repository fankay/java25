package com.kaishengit.controller;

import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/movie")
@CrossOrigin("*")
public class MovieController {

    @GetMapping("/{id}")
    public Map<String,String> getMovie(@PathVariable Integer id) {
        Map<String,String> map = new HashMap<>();
        map.put("name","阿甘正传");
        map.put("rate","9.5");
        map.put("author","Jack");
        return map;
    }

}
