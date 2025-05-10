package com.worker.infra.dataobject.permission;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限表
 *
 *  @author
 * @date: 2023/11/6 15:31
 */
@Getter
@Setter
@ToString
@TableName("sys_permission")
public class PermissionDO implements Serializable {

    private static final long serialVersionUID = -4332566955764051667L;

    /**
     * ID（主键自增）
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父级ID（主键自增）
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 状态 0-禁用 1-启用
     */
    @TableField(value = "status")
    private Byte status;

    /**
     * 权限码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 类型 1-目录 2-菜单 3-按钮
     */
    @TableField(value = "type")
    private Byte type;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 请求路径
     */
    @TableField(value = "resource_url")
    private String resourceUrl;

    /**
     * 前端路径
     */
    @TableField(value = "router_url")
    private String routerUrl;

    /**
     * 前端名称
     */
    @TableField(value = "router_name")
    private String routerName;

    /**
     * 打开类型 1-页签 2-新窗口
     */
    @TableField(value = "open_type")
    private Byte openType;

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
