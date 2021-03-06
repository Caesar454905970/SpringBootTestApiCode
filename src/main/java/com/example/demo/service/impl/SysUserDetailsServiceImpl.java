package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.entity.SysUser;
import com.example.demo.mapper.SysMenuMapper;
import com.example.demo.mapper.SysUserMapper;
import com.example.demo.service.SysUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 用户登录，Security调用数据库对前端传递的用户密码进行判断，查询出用户的权限信息
 * 查询出用户的权限信息
 */
@Service //实现类注入spring容器中
public class SysUserDetailsServiceImpl implements UserDetailsService {

    //用于查询用户信息
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询用户信息(通过用户名)
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName,username);
        SysUser  sysUser= sysUserMapper.selectOne(queryWrapper);
        //如果没有查询到用户就抛出异常
        if(Objects.isNull(sysUser)){
            throw new RuntimeException("用户名或者密码错误");
        }

        // 查询用户的权限信息 （然后JwtAuthenticationTokenFilter里面再封装进Authentication中)
//        List<String> list = new ArrayList<>(Arrays.asList("test","admin")); //多个值添加进入集合
        List<String> list = sysMenuMapper.selectPermsByUserId(sysUser.getId());
        //把数据封装成SysUserDetailsService返回
        return new SysUserDetailsService(sysUser,list);
    }
}
