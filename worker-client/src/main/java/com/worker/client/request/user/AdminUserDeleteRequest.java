package com.worker.client.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 账号删除请求
 *
 *  @author
 * @date: 2023/11/9 20:58
 */
@Getter
@Setter
@ApiModel(description = "账号删除请求")
public class AdminUserDeleteRequest implements Serializable {

    private static final long serialVersionUID = -3116089935741616487L;

    @NotNull(message = "账号id不能为空")
    @ApiModelProperty(value = "账号ids" , required = true)
    private List<Long> ids;

    @NotNull(message = "删除参数不能为空")
    @ApiModelProperty(value = "删除 1-删除", required = true)
    private Byte delete;
}
