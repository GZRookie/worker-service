package com.worker.client.request.attendance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 打卡请求对象
 */
@Getter
@Setter
@ApiModel(description = "打卡请求对象")
public class ClockInRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工人ID", required = true)
    @NotNull(message = "工人ID不能为空")
    private Long workerId;

    @ApiModelProperty(value = "打卡类型（1-上班打卡，2-下班打卡）", required = true)
    @NotNull(message = "打卡类型不能为空")
    private Byte clockType;

    @ApiModelProperty(value = "备注")
    private String remark;
}