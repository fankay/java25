package com.kaishengit.tms.controller;

import com.google.common.collect.Maps;
import com.kaishengit.tms.entity.Account;
import com.kaishengit.tms.entity.Roles;
import com.kaishengit.tms.service.AccountService;
import com.kaishengit.tms.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 账号管理控制器
 * @author fankay
 */
@Controller
@RequestMapping("/manage/account")
public class AccountController {

    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private AccountService accountService;

    @GetMapping
    public String home(Model model,
                       @RequestParam(required = false) Integer rolesId,
                       @RequestParam(required = false) String nameMobile) {

        Map<String,Object> requestParam = Maps.newHashMap();
        requestParam.put("nameMobile",nameMobile);
        requestParam.put("rolesId",rolesId);

        model.addAttribute("accountList",accountService.findAllAccountWithRolesByQueryParam(requestParam));
        model.addAttribute("rolesList",rolePermissionService.findAllRoles());
        return "manage/account/home";
    }

    /**
     * 新增角色
     * @param model
     * @return
     */
    @GetMapping("/new")
    public String newAccount(Model model) {
        List<Roles> rolesList = rolePermissionService.findAllRoles();

        model.addAttribute("rolesList",rolesList);
        return "manage/account/new";
    }

    @PostMapping("/new")
    public String newAccount(Account account, Integer[] rolesIds) {
        accountService.saveAccount(account,rolesIds);
        return "redirect:/manage/account";
    }
}
