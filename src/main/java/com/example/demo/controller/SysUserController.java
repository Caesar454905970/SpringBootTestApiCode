package com.example.demo.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.UserConstants;
import com.example.demo.entity.ResponseResult;
import com.example.demo.entity.SysUser;

import com.example.demo.framework.web.domain.AjaxResult;
import com.example.demo.mapper.SysUserMapper;
import com.example.demo.service.ISysUserService;
import com.example.demo.service.SysUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author demo
 * @since 2022-01-18
 */
@RestController

public class SysUserController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysUserMapper sysUserMapper;



    @RequestMapping("/sysUser")
    @PreAuthorize("hasAuthority('system:user:list1')") //判断当前请求该接口的用户是否具有，test的权限
    //@PreAuthorize("hasAnyAuthority('admin','test','system:dept:list')") //多个权限：用户只要有这三个权限中的任意一个都可以进行访问，

    //@PreAuthorize("hasRole('system:dept:list')")//会自动拼接一个ROle_的前缀_
    //@PreAuthorize("hasAnyRole('admin','system:dept:list')") //会自动拼接一个ROle_的前缀_；多个权限：用户只要有这三个权限中的任意一个都可以进行访问，

    //自定义权限校验的规则：expression/SGExpressionRoot
    //@PreAuthorize("@ex.hasAuthority('system:user:list1')")
    public String hello(){
        return "hello";
    }

    @Autowired
    private ISysUserService iSysUserService;


    /***
     * 用户登录
     * @param sysUser
     * @return
     */
    @PostMapping("/sysUser/login")
    public ResponseResult login(@RequestBody SysUser sysUser){
        //登录
        //  调用servie
        return iSysUserService.login(sysUser); //alt+回车，自动创建方法
    }


    /***
     * 用户注销
     * @return
     */
    @PostMapping("/sysUser/logout")
    public ResponseResult logout(){
        //登录
        //  调用servie
        return iSysUserService.logout(); //alt+回车，自动创建方法
    }



    /***
     * 用户列表查询
     * @return
     */
    @PostMapping("/sysUser/List")
    public ResponseResult<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
//        if (StrUtil.isNotBlank(search)) {
//            wrapper.like(Role::getName, search);
//        }
        Page<SysUser> RolePage = sysUserMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
//        System.out.println("************");
//        //断点调试捕获 列表
//        System.out.println(RolePage.getRecords());
//        //list写入excle文件
//        EasyExcel.write("C:\\Users\\521\\Desktop\\sys_user_副本1.xls")
//                .head(SysUser.class) //每一列的标题
//                .excelType(ExcelTypeEnum.XLS)
//                .sheet("sys_user")
//                .doWrite(RolePage.getRecords());
//        System.out.println("************");
        return new ResponseResult(200,"查询用户列表成功",RolePage);



    }


    /**
     * 新增用户
     * @param sysUser
     * @return
     */
    @PostMapping("/sysUser/add")
    public AjaxResult add(@Validated @RequestBody SysUser sysUser) {
        System.out.println(sysUser.getUserName());
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(sysUser.getUserName())))
        {
            return AjaxResult.error("新增用户'" + sysUser.getUserName() + "'失败，登录账号已存在");
        }
        else if(UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(sysUser))){
            return AjaxResult.error("新增用户'" + sysUser.getPhonenumber() + "'失败，手机号码已存在");
        }
        else if(UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(sysUser))){
             return AjaxResult.error("新增用户'" + sysUser.getUserName() + "'失败，邮箱已经存在");
        }
        //设置默认的参数，：创建人
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SysUserDetailsService loginUser = (SysUserDetailsService) authentication.getPrincipal();
        System.out.println("登录的用户为："+loginUser.getSysUser().getUserName());
        System.out.println("登录的用户对象为："+loginUser.getSysUser());
        sysUser.setCreateBy(loginUser.getSysUser().getId()); //设置create_by的id为登录用户的id
        //密码加密存储
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        sysUser.setPassword(bCryptPasswordEncoder.encode(sysUser.getPassword()));
        userService.insertUser(sysUser);
        return new AjaxResult(200,"创建用户成功",sysUser);
    }


    /**
     * 多表联查：分页查询：用户对应的角色信息：查询一个用户id的所有权限列表
     */
    @PostMapping("/hello1")
    public AjaxResult add1() {
        //创建page对象
        Page<SysUser> page = new Page<>();
        //设置每页大小
        page.setSize(2);
        //设置当前页码
        page.setCurrent(1);
        sysUserMapper.selectMenuByUserId(page);
        System.out.println(page); //获取传到的数据
        System.out.println(page.getRecords()); //获取传到的数据
        System.out.println(page.getTotal()); //获取总的记录数
        JSONObject  ajaxResult =new JSONObject();
//        JSONArray arr =new JSONArray();
        ajaxResult.put("list",page); //存放data数组
        return new AjaxResult(200,"创建用户成功",ajaxResult);
//        return  ajaxResult(200,"创建用户成功",ajaxResult);
    }




}
