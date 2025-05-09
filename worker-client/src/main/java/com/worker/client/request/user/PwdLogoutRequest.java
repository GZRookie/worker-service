package com.worker.client.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 账号密码登出请求
 *
 *  @author
 * @date: 2023/11/4 2:00
 */
@Getter
@Setter
@ApiModel(description = "账号密码登出请求")
public class PwdLogoutRequest implements Serializable {

    private static final long serialVersionUID = 7362877402408605386L;

    @NotNull(message = "用户Id不能为空")
    @ApiModelProperty(value = "用户Id", required = true)
    private Long adminUserId;
}
