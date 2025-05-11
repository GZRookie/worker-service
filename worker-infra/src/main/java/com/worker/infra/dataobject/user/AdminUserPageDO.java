package com.worker.infra.dataobject.user;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 后台用户分页DO
 *
 *  @author
 * @date: 2023/11/9 17:53
 */
@Getter
@Setter
@ToString
public class AdminUserPageDO {

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 手机号
     */
    @TableField("phone_num")
    private String phoneNum;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 账号名称
     */
    @TableField("real_name")
    private String realName;

    /**
     * 角色ID
     */
    @TableField(value = "roleId")
    private Long roleId;

    /**
     * 角色名称
     */
    @TableField("roleNames")
    private String roleNames;

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
