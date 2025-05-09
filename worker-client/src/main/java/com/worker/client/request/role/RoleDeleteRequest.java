package com.worker.client.request.role;

import com.worker.common.base.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 删除角色请求
 *
 *  @author
 * @date: 2023/11/7 23:52
 */
@Getter
@Setter
@ApiModel(description = "删除角色请求")
public class RoleDeleteRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 898079964976580096L;

    @NotNull(message = "角色id不能为空")
    @ApiModelProperty(value = "角色ids" , required = true)
    private List<Long> ids;

    @ApiModelProperty(value = "删除 1-删除", required = true)
    private Byte delete;
}
