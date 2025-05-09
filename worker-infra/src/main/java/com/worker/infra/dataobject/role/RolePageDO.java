package com.worker.infra.dataobject.role;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 角色页面
 *
 *  @author
 * @date: 2023/11/13 14:29
 */
@Getter
@Setter
@ToString
public class RolePageDO {

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 人员明细
     */
    @TableField(value = "admin_user_count")
    private Integer adminUserCount;

    /**
     * 状态 0-禁用 1-启用
     */
    @TableField(value = "status")
    private Byte status;

    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private Date createdTime;
}
