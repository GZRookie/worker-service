package com.worker.client.request.salary;

import com.worker.common.base.request.BasePageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 工资记录分页请求对象
 */
@Getter
@Setter
@ApiModel(description = "工资记录分页请求对象")
public class SalaryRecordPageRequest extends BasePageRequest implements Serializable {

    private static final long serialVersionUID = -8790784379600023152L;

    @ApiModelProperty(value = "工人姓名")
    private String workerName;
}