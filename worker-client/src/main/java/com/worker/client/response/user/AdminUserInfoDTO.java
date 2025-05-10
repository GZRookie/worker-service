package com.worker.client.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台用户响应类
 *
 *  @author
 * @date: 2023/11/4 2:09
 */
@Getter
@Setter
@ToString
@ApiModel(description = "后台用户响应类")
public class AdminUserInfoDTO implements Serializable {

    private static final long serialVersionUID = 8902324965999561267L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "状态 0-禁用 1-启用")
    private Byte status;

    @ApiModelProperty(value = "手机号")
    private String phoneNum;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "名称")
    private String realName;

    @ApiModelProperty(value = "创建人ID")
    private Long creator;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;
}
