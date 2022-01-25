package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.ResponseResult;
import com.example.demo.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author demo
 * @since 2022-01-18
 */
public interface ISysUserService extends IService<SysUser> {


    ResponseResult login(SysUser sysUser);

    ResponseResult logout();




    ResponseResult sysUserList(Integer pageNum, Integer pageSize);
}
