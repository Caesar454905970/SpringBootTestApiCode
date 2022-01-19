package com.example.demo.controller;


import com.example.demo.entity.ResponseResult;
import com.example.demo.entity.SysUser;
import com.example.demo.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author demo
 * @since 2022-01-18
 */
@RestController

public class SysUserController {



    @RequestMapping("/sysUser")
    @PreAuthorize("hasAuthority('system:user:list1')") //判断当前请求该接口的用户是否具有，test的权限
    //@PreAuthorize("hasAnyAuthority('admin','test','system:dept:list')") //多个权限：用户只要有这三个权限中的任意一个都可以进行访问，

    //@PreAuthorize("hasRole('system:dept:list')")//会自动拼接一个ROle_的前缀_
    //@PreAuthorize("hasAnyRole('admin','system:dept:list')") //会自动拼接一个ROle_的前缀_；多个权限：用户只要有这三个权限中的任意一个都可以进行访问，

    //自定义权限校验的规则：expression/SGExpressionRoot
    //@PreAuthorize("@ex.hasAuthority('system:user:list1')")
    public String hello(){
        return "hello";
    }

    @Autowired
    private ISysUserService iSysUserService;

    @PostMapping("/sysUser/login")
    public ResponseResult login(@RequestBody SysUser sysUser){
        //登录
        //  调用servie
        return iSysUserService.login(sysUser); //alt+回车，自动创建方法
    }

    @PostMapping("/sysUser/logout")
    public ResponseResult logout(){
        //登录
        //  调用servie
        return iSysUserService.logout(); //alt+回车，自动创建方法
    }

}
