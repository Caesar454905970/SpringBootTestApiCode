package com.example.demo.service;

import com.example.demo.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


//封装用户的信息

/**
 * 登录认证的时候，AuthenticationManager的authenticate方法来进行用户认证
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserDetailsService implements UserDetails {

    private SysUser sysUser;


    //用于返回权限信息
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    //用于获取用户密码
    @Override
    public String getPassword() {
        return sysUser.getPassword();//对应数据库隐射的字段
    }
    //用于获取用户密码
    @Override
    public String getUsername() {
        return sysUser.getUserName(); //对应数据库隐射的字段
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;//全部改为true
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //全部改为true
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //是否超时 全部改为true
    }

    @Override
    public boolean isEnabled() {
        return true;  //可用状态 全部改为true
    }
}
