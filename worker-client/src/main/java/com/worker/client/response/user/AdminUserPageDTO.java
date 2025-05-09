package com.worker.client.response.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 后台用户页面响应类
 *
 *  @author
 * @date: 2023/11/9 20:30
 */
@Getter
@Setter
@ToString
@ApiModel(description = "后台用户页面响应类")
public class AdminUserPageDTO implements Serializable {

    private static final long serialVersionUID = -4809134023130542080L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String realName;

    @ApiModelProperty(value = "状态 0-禁用 1-启用")
    private Byte status;

    @ApiModelProperty(value = "手机号")
    private String phoneNum;

    @ApiModelProperty(value = "角色名称")
    private String roleNames;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;
}
