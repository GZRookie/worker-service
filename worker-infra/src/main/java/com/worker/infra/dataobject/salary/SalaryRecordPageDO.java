package com.worker.infra.dataobject.salary;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 工资记录分页数据对象
 */
@Getter
@Setter
public class SalaryRecordPageDO implements Serializable {

    private static final long serialVersionUID = -5482180432846574019L;

    /**
     * 工人ID
     */
    private Long workerId;

    /**
     * 工人姓名
     */
    private String workerName;

    /**
     * 工种ID
     */
    private Long roleId;

    /**
     * 工种名称
     */
    private String roleName;

    /**
     * 工号
     */
    private String workerNo;

    /**
     * 工作天数
     */
    private Integer workDays;
}