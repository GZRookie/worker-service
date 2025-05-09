package com.worker.client.request.user;

import com.worker.common.base.request.BasePageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 账号页面请求
 *
 *  @author
 * @date: 2023/11/9 16:04
 */
@Getter
@Setter
@ApiModel(description = "账号页面请求")
public class AdminUserPageRequest extends BasePageRequest implements Serializable {

    private static final long serialVersionUID = -8266495121834437512L;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "启用状态 0-禁用 1-启用")
    private Byte status;
}
