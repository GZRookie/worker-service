package com.worker.client.response.role;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色响应类
 *
 *  @author
 * @date: 2023/11/7 23:11
 */
@Getter
@Setter
@ToString
@ApiModel(description = "角色响应类")
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 4196781644747650216L;

    @ApiModelProperty(value = "角色id", required = true)
    private Long id;

    @ApiModelProperty(value = "序号", required = true)
    private Integer num;

    @ApiModelProperty(value = "角色名称", required = true)
    private String roleName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createdTime;

    @ApiModelProperty(value = "人数", required = true)
    private Integer adminUserCount;

    @ApiModelProperty(value = "启用状态 1-启用 0-禁用", required = true)
    private Byte status;

    @ApiModelProperty(value = "是否是工人角色", required = true)
    private Boolean isWorker;
}
