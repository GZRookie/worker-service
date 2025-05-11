package com.worker.client.response.salary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 工种工资DTO
 */
@Getter
@Setter
@ApiModel(description = "工种工资DTO")
public class WorkTypeWageDTO implements Serializable {

    private static final long serialVersionUID = -3134237200130612375L;

    @ApiModelProperty(value = "序号")
    private Integer num;

    @ApiModelProperty(value = "工种ID（对应角色ID）")
    private Long roleId;

    @ApiModelProperty(value = "工种名称")
    private String roleName;

    @ApiModelProperty(value = "日工资")
    private BigDecimal dailyWage;
}