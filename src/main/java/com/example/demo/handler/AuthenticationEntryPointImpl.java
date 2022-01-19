package com.example.demo.handler;

import com.alibaba.fastjson.JSON;

import com.example.demo.entity.ResponseResult;
import com.example.demo.utils.WebUtils;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录失败返回的错误提示
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseResult result = new ResponseResult(401,"用户认证失败请重新登录");
        String json = JSON.toJSONString(result);
        //处理异常
        WebUtils.renderString(response,json);
    }
}
