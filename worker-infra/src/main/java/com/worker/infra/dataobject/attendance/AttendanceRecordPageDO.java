package com.worker.infra.dataobject.attendance;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 考勤数据对象
 */
@Getter
@Setter
public class AttendanceRecordPageDO implements Serializable {

    private static final long serialVersionUID = -7891593319036146330L;

    /**
     * 记录ID
     */
    private Long id;

    /**
     * 工人ID
     */
    private Long workerId;

    /**
     * 工人姓名
     */
    private String workerName;

    /**
     * 打卡类型（1-上班打卡，2-下班打卡）
     */
    private Byte clockType;

    /**
     * 打卡状态（1-正常，2-迟到，3-早退）
     */
    private Byte clockStatus;

    /**
     * 请假ID
     */
    private Long leaveId;

    /**
     * 请假类型（1-事假，2-病假，3-年假，4-其他）
     */
    private Byte leaveType;

    /**
     * 打卡时间
     */
    private Date clockTime;

    /**
     * 请假开始时间
     */
    private Date leaveStartTime;

    /**
     * 请假结束时间
     */
    private Date leaveEndTime;

    /**
     * 请假原因
     */
    private String leaveReason;

    /**
     * 备注
     */
    private String remark;
}