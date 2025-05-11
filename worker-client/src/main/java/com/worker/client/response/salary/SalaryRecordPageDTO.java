package com.worker.client.response.salary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 工资记录分页DTO
 */
@Getter
@Setter
@ApiModel(description = "工资记录分页DTO")
public class SalaryRecordPageDTO implements Serializable {

    private static final long serialVersionUID = 2951817460501056132L;

    @ApiModelProperty(value = "记录ID")
    private Long id;

    @ApiModelProperty(value = "序号")
    private Integer num;

    @ApiModelProperty(value = "工人ID")
    private Long workerId;

    @ApiModelProperty(value = "工人姓名")
    private String workerName;

    @ApiModelProperty(value = "工种ID（对应角色ID）")
    private Long roleId;

    @ApiModelProperty(value = "工种")
    private String roleName;

    @ApiModelProperty(value = "工号")
    private String workerNo;

    @ApiModelProperty(value = "日工资单价")
    private BigDecimal dailyWage;

    @ApiModelProperty(value = "工作天数")
    private Integer workDays;

    @ApiModelProperty(value = "总工资")
    private BigDecimal totalSalary;
}