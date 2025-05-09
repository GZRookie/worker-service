package com.worker.common.base.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@ApiModel(description = "请求基类")
public class BaseRequest implements Serializable {

    private static final long serialVersionUID = -4907478728112574475L;

    @ApiModelProperty(value = "当前登录后台用户id", hidden = true)
    private Long currLoginAdminUserId;

    @ApiModelProperty(value = "当前登录前台用户id", hidden = true)
    private Long currLoginUserId;
}