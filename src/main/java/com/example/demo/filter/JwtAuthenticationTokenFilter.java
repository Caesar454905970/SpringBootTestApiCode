package com.example.demo.filter;


import com.example.demo.entity.ResponseResult;
import com.example.demo.service.SysUserDetailsService;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * OncePerRequestFilter可以实现，所有的请求只会通过这个过滤器一次
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        //获取token
        String token = request.getHeader("token");//从请求头中获取
        if (!StringUtils.hasText(token)) { //判断是否为空
            //放行：没有token,后面还有其他的过滤器，当前就不为认证的状态了
            filterChain.doFilter(request, response);
            return ;
        }


        //解析token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject(); //获取用户的userid
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }


        //从redis中获取用户信息
        String redisKey = "login:" + userid;
        SysUserDetailsService sysUserDetails = redisCache.getCacheObject(redisKey); //从redis中获取用户的信息（已经登陆了userid)
        if(Objects.isNull(sysUserDetails)){
            //用户的信息为空
            throw new RuntimeException("用户未登录");
        }


        //存入SecurityContextHolder：后面所有的过滤器都从SecurityContextHolder拿用户信息（登录的时候也可以直接存入了）
        //TODO 获取权限信息封装到Authentication中
        /**
         * 传入三个参数：用户信息 ，null ,权限信息
         */
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(sysUserDetails,null,sysUserDetails.getAuthorities());//把用户的状态置为已认证
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }
}
