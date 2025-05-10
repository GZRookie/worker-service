package com.worker.client.request.permission;

import com.worker.common.base.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 权限页面请求
 *
 *  @author
 * @date: 2023/11/5 19:14
 */
@Getter
@Setter
@ApiModel(description = "权限页面请求")
public class PermissionPageRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = -7931605142201488344L;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "启用状态 0-禁用 1-启用")
    private Byte status;
}
