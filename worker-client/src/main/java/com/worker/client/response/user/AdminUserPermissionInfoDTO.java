package com.worker.client.response.user;

import com.worker.client.response.permisiion.PermissionNodeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 后台用户权限响应类
 *
 *  @author
 * @date: 2023/11/4 2:09
 */
@Getter
@Setter
@ToString
@ApiModel(description = "后台用户权限响应类")
public class AdminUserPermissionInfoDTO implements Serializable {

    private static final long serialVersionUID = 2101585265329537438L;

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "权限列表")
    private List<PermissionNodeDTO> permissionList;
}
