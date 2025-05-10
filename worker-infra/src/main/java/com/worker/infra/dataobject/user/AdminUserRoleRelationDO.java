package com.worker.infra.dataobject.user;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台用户角色关联关系表
 *
 *  @author
 * @date: 2023/11/9 21:51
 */
@Getter
@Setter
@ToString
@TableName("sys_user_role_relation")
public class AdminUserRoleRelationDO implements Serializable {

    private static final long serialVersionUID = -7807909791539592414L;

    /**
     * ID（主键自增）
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 账号id
     */
    @TableField(value = "admin_user_id")
    private Long adminUserId;

    /**
     * 角色id
     */
    @TableField(value = "admin_role_id")
    private Long adminRoleId;

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
