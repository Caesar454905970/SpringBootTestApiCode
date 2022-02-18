//package com.example.demo.entity;
//
//import com.alibaba.excel.annotation.ExcelProperty;
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//
//import java.io.Serializable;
//
//@Data
//@EqualsAndHashCode(callSuper = false)
//@TableName("sys_user") //实体类对应的数据库
//public class SysUser1 implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 主键
//     */
//    @ExcelProperty("id") //指定读取excle的列名
//    @TableId(value = "id", type = IdType.AUTO)
//    private Long id;
//
//    /**
//     * 用户名
//     */
//    @ExcelProperty("user_name")
//    private String userName;
//
//    /**
//     * 昵称
//     */
//    @ExcelProperty("nick_name")
//    private String nickName;
//
//
//    /**
//     * 关联表：查询一个用户id的所有权限列表
//     */
//    @TableField(exist = false) //不映射此字段
//    private String menuName;
//    @TableField(exist = false)
//    private Long menuID;
//    @TableField(exist = false)
//    private Long roleID;
//
//}
