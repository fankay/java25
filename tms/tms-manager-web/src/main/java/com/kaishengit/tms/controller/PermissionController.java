package com.kaishengit.tms.controller;

import com.kaishengit.tms.entity.Permission;
import com.kaishengit.tms.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 权限控制器
 * @author fankay
 */
@Controller
@RequestMapping("/manage/permission")
public class PermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 权限的首页
     * @return
     */
    @GetMapping
    public String home(Model model) {
        List<Permission> permissionList = rolePermissionService.findAllPermission();
        model.addAttribute("permissionList",permissionList);
        return "manage/permission/home";
    }

    /**
     * 新增权限
     * @return
     */
    @GetMapping("/new")
    public String newPermission(Model model) {
        //查询菜单类型的权限列表
        List<Permission> permissionList = rolePermissionService.findPermissionByPermissionType(Permission.MENU_TYPE);
        model.addAttribute("permissionList",permissionList);
        return "manage/permission/new";
    }

    @PostMapping("/new")
    public String newPermission(Permission permission, RedirectAttributes redirectAttributes) {
        rolePermissionService.savePermission(permission);

        redirectAttributes.addFlashAttribute("message","新增权限成功");
        return "redirect:/manage/permission";
    }
}
