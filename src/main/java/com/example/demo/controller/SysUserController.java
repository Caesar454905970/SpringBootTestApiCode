package com.example.demo.controller;


import com.example.demo.entity.ResponseResult;
import com.example.demo.entity.SysUser;
import com.example.demo.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
