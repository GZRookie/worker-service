package com.worker.infra.dataobject.role;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色关联权限表
 *
 *  @author
 * @date: 2023/11/8 16:05
 */
@Getter
@Setter
@ToString
@TableName("admin_role_resource_relation")
public class RolePermissionRelationDO implements Serializable {

    private static final long serialVersionUID = 8476671408781881264L;

    /**
     * ID（主键自增）
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色id
     */
    @TableField(value = "admin_role_id")
    private Long adminRoleId;

    /**
     * 权限id
     */
    @TableField(value = "admin_resource_id")
    private Long adminResourceId;

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
