package com.worker.client.request.permission;

import com.worker.common.base.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 新增权限请求
 *
 *  @author
 * @date: 2023/11/5 14:32
 */
@Getter
@Setter
@ApiModel(description = "新增或编辑权限请求")
public class PermissionRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 5033151531240437478L;

    @ApiModelProperty(value = "权限id，编辑必传")
    private Long id;

    @ApiModelProperty(value = "父类权限id")
    private Long parentId;

    @NotNull(message = "请求类型不能为空")
    @ApiModelProperty(value = "请求类型，1-新增 2-编辑", required = true)
    private Byte requestType;

    @NotNull(message = "权限类型不能为空")
    @ApiModelProperty(value = "权限类型，1-目录 2-菜单 3-按钮", required = true)
    private Byte type;

    @NotEmpty(message = "权限名称不能为空")
    @ApiModelProperty(value = "权限名称", required = true)
    private String name;

    @ApiModelProperty(value = "权限码")
    private String code;

    @NotNull(message = "排序不能为空")
    @ApiModelProperty(value = "排序", required = true)
    private Integer sort;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "菜单状态 1-显示 0-隐藏")
    private Byte status;

    @ApiModelProperty(value = "请求地址")
    private String resourceUrl;

    @ApiModelProperty(value = "routerUrl")
    private String routerUrl;

    @ApiModelProperty(value = "routerName")
    private String routerName;

    @ApiModelProperty(value = "打开方式 1-页签 2-新窗口")
    private Byte openType;
}
