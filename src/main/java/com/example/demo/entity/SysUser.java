package com.example.demo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author demo
 * @since 2022-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user") //实体类对应的数据库
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty("id") //指定读取excle的列名
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @ExcelProperty("user_name")
    private String userName;

    /**
     * 昵称
     */
    @ExcelProperty("nick_name")
    private String nickName;

    /**
     * 密码
     */
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) :加上就不会返回给前端
    @ExcelProperty("password")
    private String password;

    /**
     * 账号状态（0正常 1停用）
     */
    @ExcelProperty("status")
    private String status;

    /**
     * 邮箱
     */
    @ExcelProperty("email")
    private String email;

    /**
     * 手机号
     */
    @ExcelProperty("phonenumber")
    private String phonenumber;

    /**
     * 用户性别（0男，1女，2未知）
     */
    @ExcelProperty("sex")
    private String sex;

    /**
     * 头像
     */
    @ExcelProperty("avatar")
    private String avatar;

    /**
     * 用户类型（0管理员，1普通用户）
     */
    @ExcelProperty("user_type")
    private String userType;

    /**
     * 创建人的用户id
     */
    @ExcelProperty("create_by")
    private Long createBy;

    /**
     * 创建时间
     */
    @ExcelProperty("create_time")
    @TableField(fill = FieldFill.INSERT) //自动填充
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @ExcelProperty("update_by")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ExcelProperty("update_time")
    @TableField(fill = FieldFill.INSERT_UPDATE) //自动填充
    private LocalDateTime updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @ExcelProperty("del_flag")
    private Integer delFlag;

    /**
     *乐观锁：保证对数据的操作不发生冲突
     */
    @Version
    @ExcelProperty("version")
    @TableField(fill = FieldFill.INSERT) //自动填充
    private Integer version;


    /**
     * 关联表：查询一个用户id的所有权限列表
     */
    @TableField(exist=false) //不映射此字段
    private String menuName;
    @TableField(exist=false)
    private Long menuID;
    @TableField(exist=false)
    private Long roleID;

}
