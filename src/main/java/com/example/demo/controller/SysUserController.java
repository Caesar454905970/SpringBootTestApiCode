package com.example.demo.controller;


import com.example.demo.entity.SysUser;
import com.example.demo.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


}
