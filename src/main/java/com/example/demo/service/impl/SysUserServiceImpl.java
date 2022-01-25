package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.ResponseResult;
import com.example.demo.entity.SysUser;
import com.example.demo.mapper.SysUserMapper;
import com.example.demo.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.service.SysUserDetailsService;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 用户表 服务实现类 :实现SysUsrService接口
 * </p>
 *
 * @author demo
 * @since 2022-01-18
 */
@Service //注入容器
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {


    @Autowired
    private SysUserMapper sysUserMapper;

    public void testSysUserMapper(){
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        System.out.println(sysUsers);
    }

    @Autowired
    private RedisCache redisCache;


    @Autowired //SecutityConfig里面暴露出来的AuthenticationManager：用于认证
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseResult login(SysUser sysUser) {
        //AuthenticationManager的authenticate方法来进行用户认证


        //AuthenticationManager调用用户名密码，完成认证操作：主要调用UserDetails里面查询数据库的方法
        UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(sysUser.getUserName(),sysUser.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //如果认证没通过，给出对应的提示
        if(Objects.isNull(authenticate)){
            //认证没通过,抛出异常，结束
            throw new RuntimeException("登录失败");
        }

        //如果认证通过，使用userid生成一个jwt jwt存入responseResult返回
        SysUserDetailsService sysUserDetails =(SysUserDetailsService) authenticate.getPrincipal();//强制转换
        String userid = sysUserDetails.getSysUser().getId().toString(); //获取用户的ID(long),转换为字符串
        String  jwt = JwtUtil.createJWT(userid); //把userid生成token


        //把完整的信息存入redis userid作为key,用户信息+权限信息作为value
        redisCache.setCacheObject("login:"+userid,sysUserDetails);



        //也可以使用自带的响应式ResponseEntity
        Map<String, String> map = new HashMap<>(); //存放data:token
        map.put("token",jwt);
        return new ResponseResult(200,"登录成功",map);
    }

    @Override
    public ResponseResult logout() {
        //获取上下文中SecurityContextHolder的用户id或者用户信息，不需要对SecurityContextHolder里面的用户信息进行删除
        UsernamePasswordAuthenticationToken authentication =(UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        SysUserDetailsService sysUserDetails = (SysUserDetailsService)  authentication.getPrincipal();
        Long userid=sysUserDetails.getSysUser().getId();//获取userid
        //删除redis中的值
        redisCache.deleteObject("login:"+userid);


        return new ResponseResult(200,"注销成功");
    }

    @Override
    public ResponseResult sysUserList(Integer pageNum, Integer pageSize) {
//        IPage<SysUser> page = new Page<>();
//        //设置每页条数
//        page.setSize(2);
//        //设置查询第几页
//        page.setCurrent(1);

        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        System.out.println(sysUsers);
        return new ResponseResult(200,"查询所有用户成功",sysUsers);
    }


}
