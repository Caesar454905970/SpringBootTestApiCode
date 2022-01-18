package com.example.demo.service.impl;

import com.example.demo.entity.SysUser;
import com.example.demo.mapper.SysUserMapper;
import com.example.demo.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author demo
 * @since 2022-01-18
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {


    @Autowired
    private SysUserMapper sysUserMapper;

    public void testSysUserMapper(){
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        System.out.println(sysUsers);
    }
}
