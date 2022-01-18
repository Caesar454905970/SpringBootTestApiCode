package com.example.demo.service;

import com.example.demo.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author demo
 * @since 2022-01-18
 */
public interface ISysUserService extends IService<SysUser> {
    void testSysUserMapper();
}
