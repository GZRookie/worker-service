package com.worker.client.request.attendance;

import com.worker.common.base.request.BasePageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 考勤记录分页查询请求对象
 */
@Getter
@Setter
@ApiModel(description = "考勤记录分页查询请求对象")
public class AttendanceRecordPageRequest extends BasePageRequest implements Serializable {

    private static final long serialVersionUID = -4802615523601487291L;

    @ApiModelProperty(value = "工人姓名")
    private String workerName;

    @ApiModelProperty(value = "开始日期")
    private Date startDate;

    @ApiModelProperty(value = "结束日期")
    private Date endDate;
}