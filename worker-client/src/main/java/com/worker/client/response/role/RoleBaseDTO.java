package com.worker.client.response.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 角色基本信息响应类
 *
 *  @author
 * @date: 2023/11/13 1:09
 */
@Getter
@Setter
@ToString
@ApiModel(description = "角色基本信息响应类")
public class RoleBaseDTO implements Serializable {

    private static final long serialVersionUID = -9107597419441873641L;

    @ApiModelProperty(value = "角色id", required = true)
    private Long id;

    @ApiModelProperty(value = "角色名称", required = true)
    private String roleName;
}
