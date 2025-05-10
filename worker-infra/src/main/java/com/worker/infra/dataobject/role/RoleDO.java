package com.worker.infra.dataobject.role;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表
 *
 *  @author
 * @date: 2023/11/8 15:10
 */
@Getter
@Setter
@ToString
@TableName("sys_role")
public class RoleDO implements Serializable {

    private static final long serialVersionUID = -1420871520686783547L;

    /**
     * ID（主键自增）
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    private String roleName;

    /**
     * 状态 0-禁用 1-启用
     */
    @TableField(value = "status")
    private Byte status;

    /**
     * 删除 0-未删除 1-删除
     */
    @TableField(value = "`delete`")
    private Byte delete;

    /**
     * 创建人ID
     */
    @TableField(value = "creator", fill = FieldFill.INSERT)
    private Long creator;

    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private Date createdTime;
}
