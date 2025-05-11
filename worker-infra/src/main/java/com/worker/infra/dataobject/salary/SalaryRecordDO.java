package com.worker.infra.dataobject.salary;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 工资记录数据对象
 */
@Getter
@Setter
public class SalaryRecordDO implements Serializable {

    private static final long serialVersionUID = 339048044198827410L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 工人ID
     */
    private Long workerId;
    
    /**
     * 工作日期
     */
    private Date workDate;
    
    /**
     * 上班打卡ID
     */
    private Long clockInId;
    
    /**
     * 下班打卡ID
     */
    private Long clockOutId;
    
    /**
     * 请假记录ID
     */
    private Long leaveId;
    
    /**
     * 出勤状态（1-正常，2-迟到，3-早退，4-迟到早退，5-请假，6-缺勤）
     */
    private Byte attendanceStatus;
    
    /**
     * 日工资单价
     */
    private BigDecimal dailyWage;
    
    /**
     * 实际工资
     */
    private BigDecimal actualWage;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建人
     */
    private Long creator;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新人
     */
    private Long updater;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 是否删除（0-否，1-是）
     */
    private Byte delete;
}