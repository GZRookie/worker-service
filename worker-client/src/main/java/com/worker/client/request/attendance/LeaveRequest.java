package com.worker.client.request.attendance;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 请假请求对象
 */
@Getter
@Setter
@ApiModel(description = "请假请求对象")
public class LeaveRequest implements Serializable {

    private static final long serialVersionUID = -6402759233179785323L;

    @ApiModelProperty(value = "请假记录ID（编辑时必填）")
    private Long id;

    @ApiModelProperty(value = "工人ID", required = true)
    @NotNull(message = "工人ID不能为空")
    private Long workerId;

    @ApiModelProperty(value = "请假类型（1-事假，2-病假，3-年假，4-其他）", required = true)
    @NotNull(message = "请假类型不能为空")
    private Byte leaveType;

    @ApiModelProperty(value = "请假开始时间", required = true)
    @NotNull(message = "请假开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "请假结束时间", required = true)
    @NotNull(message = "请假结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "请假原因", required = true)
    @NotNull(message = "请假原因不能为空")
    private String reason;
}