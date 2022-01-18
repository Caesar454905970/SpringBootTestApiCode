package com.example.demo.mapper;

import com.example.demo.entity.SysUser;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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

}
