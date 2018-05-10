package com.kaishengit.controller;

import com.kaishengit.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping
    public String home(Model model) {

        userDao.save("SPringBoot","USA","123123");

        List<String> names = Arrays.asList();

        model.addAttribute("message","<em>你好,SpringBoot!</em>");
        model.addAttribute("age",23);
        model.addAttribute("names",names);
        model.addAttribute("id",1001);
        model.addAttribute("money",123123123123123L);
        return "user/index";
    }
}
