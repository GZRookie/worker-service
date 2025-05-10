package com.worker.client.response.worker;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 工人信息响应类
 *
 * @date: 2023/11/4 2:09
 */
@Getter
@Setter
@ToString
@ApiModel(description = "工人角色信息响应类")
public class WorkerRoleDTO implements Serializable {

    private static final long serialVersionUID = -1401624775532775843L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;
}