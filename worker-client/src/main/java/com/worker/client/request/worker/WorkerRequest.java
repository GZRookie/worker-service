package com.worker.client.request.worker;

import com.worker.common.base.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 工人信息请求
 *
 * @date: 2023/11/7 23:05
 */
@Getter
@Setter
@ApiModel(description = "工人信息请求")
public class WorkerRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 898079964976580096L;

    @ApiModelProperty(value = "工人ID")
    private Long id;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名", required = true)
    private String name;
    
    @NotNull(message = "角色ID不能为空")
    @ApiModelProperty(value = "角色ID", required = true)
    private Long roleId;
    
    @NotNull(message = "性别不能为空")
    @ApiModelProperty(value = "性别 0-女 1-男", required = true)
    private Byte gender;
    
    @NotBlank(message = "联系方式不能为空")
    @ApiModelProperty(value = "联系方式", required = true)
    private String phoneNum;
    
    @NotBlank(message = "身份证号不能为空")
    @ApiModelProperty(value = "身份证号", required = true)
    private String idCard;
    
    @ApiModelProperty(value = "紧急联系人")
    private String emergencyContact;
}