package com.worker.client.request.worker;

import com.worker.common.base.request.BasePageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 工人信息分页请求
 *
 * @date: 2023/11/9 16:04
 */
@Getter
@Setter
@ApiModel(description = "工人信息分页请求")
public class WorkerPageRequest extends BasePageRequest implements Serializable {

    private static final long serialVersionUID = -8266495121834437512L;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "工号")
    private String workerNo;

    @ApiModelProperty(value = "角色id")
    private Long roleId;
}