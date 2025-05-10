package com.worker.client.request.worker;

import com.worker.common.base.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 启用工人信息请求
 *
 * @date: 2023/11/7 23:52
 */
@Getter
@Setter
@ApiModel(description = "启用工人信息请求")
public class WorkerEnableRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 898079964976580096L;

    @NotNull(message = "工人ID不能为空")
    @ApiModelProperty(value = "工人ID" , required = true)
    private Long id;

    @ApiModelProperty(value = "启用 0-禁用 1-启用", required = true)
    private Byte enable;
}