package com.worker.client.response.attendance;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 考勤记录分页查询响应对象
 */
@Getter
@Setter
@ToString
@ApiModel(description = "考勤记录分页查询响应对象")
public class AttendanceRecordPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录ID")
    private Long id;

    @ApiModelProperty(value = "序号")
    private Integer num;

    @ApiModelProperty(value = "工人ID")
    private Long workerId;

    @ApiModelProperty(value = "工人姓名")
    private String workerName;

    @ApiModelProperty(value = "打卡类型（1-上班打卡，2-下班打卡）")
    private Byte clockType;

    @ApiModelProperty(value = "打卡类型名称")
    private String clockTypeName;

    @ApiModelProperty(value = "打卡状态（1-正常，2-迟到，3-早退）")
    private Byte clockStatus;

    @ApiModelProperty(value = "打卡状态名称")
    private String clockStatusName;

    @ApiModelProperty(value = "请假ID")
    private Long leaveId;

    @ApiModelProperty(value = "请假类型（1-事假，2-病假，3-年假，4-其他）")
    private Byte leaveType;

    @ApiModelProperty(value = "请假类型名称")
    private String leaveTypeName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "打卡时间")
    private Date clockTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "请假开始时间")
    private Date leaveStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "请假结束时间")
    private Date leaveEndTime;

    @ApiModelProperty(value = "请假原因")
    private String leaveReason;

    @ApiModelProperty(value = "备注")
    private String remark;
}