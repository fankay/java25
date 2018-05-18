package com.kaishengit.controller;

import com.kaishengit.controller.result.ResponseBean;
import com.kaishengit.entity.Account;
import com.kaishengit.service.AccountService;
import com.kaishengit.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseBean login(@RequestBody Account account) {
        try {
            Account dbAccount = accountService.login(account.getUserName(),account.getPassword());
            //创建JWT Token
            String token = jwtUtil.createToken(dbAccount.getId(),dbAccount.getPassword());
            return ResponseBean.success(token);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseBean.error(e.getMessage());
        }
    }
}
