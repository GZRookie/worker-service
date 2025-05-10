package com.worker.client.response.permisiion;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 权限基本信息响应类
 *
 *  @author
 * @date: 2023/11/13 0:38
 */
@Getter
@Setter
@ToString
@ApiModel(description = "权限基本信息响应类")
public class PermissionBaseDTO implements Serializable {

    private static final long serialVersionUID = -4403997984380852033L;

    @ApiModelProperty(value = "权限id")
    private Long id;

    @ApiModelProperty(value = "权限名称")
    private String name;
}
