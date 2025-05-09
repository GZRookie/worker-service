package com.worker.client.request.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 角色启用请求
 *
 *  @author
 * @date: 2023/11/9 20:58
 */
@Getter
@Setter
@ApiModel(description = "角色启用请求")
public class RoleEnableRequest implements Serializable {

    private static final long serialVersionUID = -3706514329076729604L;

    @NotNull(message = "角色id不能为空")
    @ApiModelProperty(value = "角色id" , required = true)
    private Long id;

    @NotNull(message = "启用参数不能为空")
    @ApiModelProperty(value = "启用 1-启用 0-禁用", required = true)
    private Byte enable;
}
