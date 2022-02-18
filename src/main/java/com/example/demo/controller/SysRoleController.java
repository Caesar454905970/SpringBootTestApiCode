package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.ResponseResult;
import com.example.demo.entity.SysRole;
import com.example.demo.entity.SysUser;
import com.example.demo.mapper.SysRoleMapper;
import com.example.demo.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author demo
 * @since 2022-01-18
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private ISysRoleService iSysRoleService;

    /***
     * 用户列表查询
     * @return
     */
    @PostMapping("/List")
    public ResponseResult<?> findPage1(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
//        if (StrUtil.isNotBlank(search)) {
//            wrapper.like(Role::getName, search);
//        }
        Page<SysRole> RolePage = sysRoleMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return new ResponseResult(200,"查询用户列表成功",RolePage);
    }

}
