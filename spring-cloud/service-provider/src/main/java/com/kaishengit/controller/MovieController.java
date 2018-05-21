package com.kaishengit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @GetMapping("/{id:\\d+}")
    public String getMovieName(@PathVariable Integer id) {
        System.out.println("ID: " + id);
        return "无间道2";
    }

    @PostMapping("/new")
    public String saveNewMovie(String movieName,String author) {
        System.out.println("MovieName:" + movieName + "  Author:" + author);
        return "save success";
    }
}
