package com.worker.client.response.permisiion;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
/**
 * 权限响应类
 *
 *  @author
 * @date: 2023/11/5 19:13
 */
@Getter
@Setter
@ToString
@ApiModel(description = "权限响应类")
public class PermissionDTO implements Serializable {

    private static final long serialVersionUID = 3200864989888320472L;

    @ApiModelProperty(value = "权限id")
    private Long id;

    @ApiModelProperty(value = "父类权限id")
    private Long parentId;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "请求地址")
    private String resourceUrl;

    @ApiModelProperty(value = "routerUrl")
    private String routerUrl;

    @ApiModelProperty(value = "routerName")
    private String routerName;

    @ApiModelProperty(value = "权限类型 1-目录 2-菜单 3-按钮")
    private Byte type;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "权限码")
    private String code;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "状态 0-禁用 1-启用")
    private Byte status;
}
