package com.example.demo.expression;
import com.example.demo.service.SysUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限校验方法
 */
@Component("ex") //注入spring容器中
public class SGExpressionRoot {

    public boolean hasAuthority(String authority){
        //获取当前用户的权限(类似于从上下文中进行获取）
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SysUserDetailsService loginUser = (SysUserDetailsService) authentication.getPrincipal();
        List<String> permissions = loginUser.getPermissions(); //权限的集合
        //判断用户权限集合中是否存在authority
        return permissions.contains(authority);
    }
}
