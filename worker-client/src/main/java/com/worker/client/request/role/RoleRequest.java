package com.worker.client.request.role;

import com.worker.common.base.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 新增角色请求
 *
 *  @author
 * @date: 2023/11/7 23:52
 */
@Getter
@Setter
@ApiModel(description = "新增或编辑权限请求")
public class RoleRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = -6297984326926178991L;

    @ApiModelProperty(value = "角色id，编辑必传")
    private Long id;

    @NotNull(message = "请求类型不能为空")
    @ApiModelProperty(value = "请求类型，1-新增 2-编辑", required = true)
    private Byte requestType;

    @NotNull(message = "启用类型不能为空")
    @ApiModelProperty(value = "启用类型，1-启用 0-禁用", required = true)
    private Byte status;

    @NotEmpty(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称", required = true)
    private String roleName;

    @NotNull(message = "权限列表不能为空")
    @ApiModelProperty(value = "权限ids", required = true)
    private List<Long> permissionIds;
}
