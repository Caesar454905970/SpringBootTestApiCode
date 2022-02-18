package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.SysUser;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author demo
 * @since 2022-01-18
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    public int checkUserNameUnique(String userName);


    /**
     * 校验手机号码是否唯一
     *
     * @param phoneNumber 手机号码
     * @return 结果
     */
    public int checkPhoneUnique(String phoneNumber);
    /**
     * 校验email是否唯一
     *
     * @param email 邮箱
     * @return 结果
     */
    public int checkEmailUnique(String email);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 查询一个用户id的所有权限列表
     */
    IPage<SysUser> selectMenuByUserId(Page<SysUser> page);

}
