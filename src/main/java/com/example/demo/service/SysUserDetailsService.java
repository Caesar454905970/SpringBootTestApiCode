package com.example.demo.service;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.demo.entity.SysUser;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


//封装用户的信息

/**
 * 登录认证的时候，AuthenticationManager的authenticate方法来进行用户认证
 */
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class SysUserDetailsService implements UserDetails {

    private SysUser sysUser;//存用户信息的成员变量

    private List<String> permissions;

    //创建一个有参构造，用于接受 sysUser permissions
    public SysUserDetailsService(SysUser sysUser,List<String> permissions){
        this.sysUser=sysUser;
        this.permissions=permissions;
    }


    @JSONField(serialize= false) //防止redis序列化
    private List<SimpleGrantedAuthority> authorities;
    //用于返回权限信息
    @Override //需要进行方法的重写
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //把permissions中的String类型的权限信息封装成SimleGrantedAuthority对象
        // 第一次进行转换。后面不为空直接调用前面的转化结果：把List<SimpleGrantedAuthority>定义成 成员变量 判断是否为空
        //但是用户权限被修改了，就必须重新登录进行写入
        if(authorities!=null){
            return authorities;
        }
        authorities=  permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
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
