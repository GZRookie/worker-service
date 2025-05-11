package com.worker.client.request.salary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 工资设置请求对象
 */
@Getter
@Setter
@ApiModel(description = "工资设置请求对象")
public class WageEditRequest implements Serializable {

    private static final long serialVersionUID = -6506347349804836603L;

    @NotNull(message = "工人ID不能为空")
    @ApiModelProperty(value = "工人ID")
    private Long roleId;

    @NotEmpty(message = "工资不能为空")
    @ApiModelProperty(value = "工资")
    private String dailyWage;
}