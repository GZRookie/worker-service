package com.worker.client.request.role;

import com.worker.common.base.request.BasePageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 角色页面请求
 *
 *  @author
 * @date: 2023/11/7 23:21
 */
@Getter
@Setter
@ApiModel(description = "角色页面请求")
public class RolePageRequest extends BasePageRequest implements Serializable {

    private static final long serialVersionUID = 291570551395190349L;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "启用状态 0-禁用 1-启用")
    private Byte status;
}
