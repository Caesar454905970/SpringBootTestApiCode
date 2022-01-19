package com.example.demo.mapper;

import com.example.demo.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author demo
 * @since 2022-01-18
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<String> selectPermsByUserId(Long userId);
}
