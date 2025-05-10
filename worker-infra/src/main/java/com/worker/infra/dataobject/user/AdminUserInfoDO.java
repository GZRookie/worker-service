package com.worker.infra.dataobject.user;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台用户表
 *
 *  @author
 * @date: 2023/11/3 14:59
 */
@Getter
@Setter
@ToString
@TableName("sys_user")
public class AdminUserInfoDO implements Serializable {

    private static final long serialVersionUID = 2303373962232361566L;

    /**
     * ID（主键自增）
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 状态 0-禁用 1-启用
     */
    @TableField(value = "status")
    private Byte status;

    /**
     * 状态 0-未删除 1-删除
     */
    @TableField(value = "`delete`")
    private Byte delete;

    /**
     * 手机号
     */
    @TableField(value = "phone_num")
    private String phoneNum;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 名称
     */
    @TableField(value = "real_name")
    private String realName;

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
