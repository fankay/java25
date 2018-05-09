package com.kaishengit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String home(Model model) {

        List<String> names = Arrays.asList("tom","jack","zhangsan");

        model.addAttribute("message","你好,SpringBoot!");
        model.addAttribute("age",11);
        model.addAttribute("names",names);
        model.addAttribute("id",1001);
        return "user/home";
    }
}
