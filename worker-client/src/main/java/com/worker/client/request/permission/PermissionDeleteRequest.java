package com.worker.client.request.permission;

import com.worker.common.base.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 删除权限请求
 *
 *  @author
 * @date: 2023/11/5 18:58
 */
@Getter
@Setter
@ApiModel(description = "启动或删除权限请求")
public class PermissionDeleteRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = -1907551202625857639L;

    @NotNull(message = "权限id不能为空")
    @ApiModelProperty(value = "权限ids" , required = true)
    private List<Long> ids;

    @ApiModelProperty(value = "删除 1-删除", required = true)
    private Byte delete;
}
