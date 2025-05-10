package com.worker.client.response.permisiion;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
/**
 * 权限响应类
 *
 *  @author
 * @date: 2023/11/5 19:13
 */
@Getter
@Setter
@ToString
@ApiModel(description = "权限响应类")
public class PermissionDTO implements Serializable {

    private static final long serialVersionUID = 3200864989888320472L;

    @ApiModelProperty(value = "权限id")
    private Long id;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "请求地址")
    private String resourceUrl;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "权限码")
    private String code;
}
