package com.worker.client.request.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 权限启用请求
 *
 *  @author
 * @date: 2023/11/9 20:58
 */
@Getter
@Setter
@ApiModel(description = "权限启用请求")
public class PermissionEnableRequest implements Serializable {

    private static final long serialVersionUID = -2467440231536870268L;

    @NotNull(message = "权限id不能为空")
    @ApiModelProperty(value = "权限id" , required = true)
    private Long id;

    @NotNull(message = "启用参数不能为空")
    @ApiModelProperty(value = "启用 1-启用 0-禁用", required = true)
    private Byte enable;
}
