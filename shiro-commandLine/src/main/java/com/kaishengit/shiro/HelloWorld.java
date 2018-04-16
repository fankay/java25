package com.kaishengit.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import java.util.Arrays;

public class HelloWorld {

    public static void main(String[] args) {

        //1. 创建SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);

        //2. 获取Subject对象
        Subject subject = SecurityUtils.getSubject();

        //3. 判断subject是否认证（登录）
        System.out.println("isAuthenticated? " + subject.isAuthenticated());

        if(!subject.isAuthenticated()) {
            //4. 根据账号和密码进行登录
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhangsan","123123");

            try {
                //5. 通过login方法进行登录,如果登录失败，会抛出AuthenticationException及其子异常
                subject.login(usernamePasswordToken);
                // getPrincipal() 返回的是当前登录的账号名称
                System.out.println(subject.getPrincipal() + "登录成功");

                //6. 通过hasRole()方法判断subject是否拥有某个角色
                System.out.println("has Admin? " + subject.hasRole("admin"));
                System.out.println("has superadmin? " + subject.hasRole("superadmin"));
                //subject.checkRole("user");同样是判断subject是否拥有某个角色，如果没有，则抛出异常
                //hasRoles方法可以同时判断subject多个角色，返回为boolean类型的数组
                boolean[] results = subject.hasRoles(Arrays.asList("admin","superadmin","user"));
                for(boolean result : results) {
                    System.out.println(result);
                }
                //hasAllRoles()判断subject是否同时拥有这些角色，如果一个没有，就返回false
                System.out.println(subject.hasAllRoles(Arrays.asList("admin","superadmin")));

                //权限判定
                System.out.println("-----------------------------------");
                //isPermitted() 判断subject是否拥有指定权限
                System.out.println("" + subject.isPermitted("user:add"));
                System.out.println("" + subject.isPermitted("customer:add"));

                boolean[] isPermittedArray = subject.isPermitted("user:add","customer:add","customer:find");
                for(boolean isPermitted : isPermittedArray) {
                    System.out.println(isPermitted);
                }

                //isPermittedAll()判断subject是否同时拥有这些权限
                System.out.println(subject.isPermittedAll("customer:add","customer:find"));
                //checkPermission()判断subject是否有该权限，如果没有则抛出异常
                //subject.checkPermission("user:add");


                //session
                Session session = subject.getSession();
                session.setAttribute("uName","jackson");

                System.out.println(session.getAttribute("uName"));


                //安全退出
                subject.logout();



            } catch (UnknownAccountException ex) {
                System.out.println("UnknownAccountException");
            } catch (IncorrectCredentialsException ex) {
                System.out.println("IncorrectCredentialsException");
            } catch (LockedAccountException ex) {
                System.out.println("LockedAccountException");
            } catch (AuthenticationException ex) {
                System.out.println("AuthenticationException");
            }
        }


    }
}
