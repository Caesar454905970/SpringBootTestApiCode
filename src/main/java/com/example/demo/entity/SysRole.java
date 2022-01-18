package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author demo
 * @since 2022-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;

    /**
     * del_flag
     */
    private Integer delFlag;

    private Long createBy;

    private LocalDateTime createTime;

    private Long updateBy;

    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;


}
