package com.worker.client.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 账号启用请求
 *
 *  @author
 * @date: 2023/11/9 20:58
 */
@Getter
@Setter
@ApiModel(description = "账号启用请求")
public class AdminUserEnableRequest implements Serializable {

    private static final long serialVersionUID = -5502615179695028205L;

    @NotNull(message = "账号id不能为空")
    @ApiModelProperty(value = "账号id" , required = true)
    private Long id;

    @NotNull(message = "启用参数不能为空")
    @ApiModelProperty(value = "启用 1-启用 0-禁用", required = true)
    private Byte status;
}
