package com.worker.client.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 账号新增请求
 *
 *  @author
 * @date: 2023/11/9 20:47
 */
@Getter
@Setter
@ApiModel(description = "账号新增请求")
public class AdminUserRequest implements Serializable {

    private static final long serialVersionUID = 311156502828976120L;

    @ApiModelProperty(value = "账号id，编辑必传")
    private Long id;

    @NotEmpty(message = "角色不能空")
    @ApiModelProperty(value = "角色ids", required = true)
    private List<Long> roleIds;

    @NotNull(message = "请求类型不能为空")
    @ApiModelProperty(value = "请求类型，1-新增 2-编辑", required = true)
    private Byte requestType;

    @NotEmpty(message = "账号名称不能空")
    @ApiModelProperty(value = "账号名称", required = true)
    private String realName;

    @NotEmpty(message = "手机号不能空")
    @ApiModelProperty(value = "手机号", required = true)
    private String phoneNum;

    @NotEmpty(message = "初始密码不能空")
    @ApiModelProperty(value = "初始密码", required = true)
    private String password;
}
