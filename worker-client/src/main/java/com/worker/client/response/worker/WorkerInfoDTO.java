package com.worker.client.response.worker;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 工人信息响应类
 *
 * @date: 2023/11/4 2:09
 */
@Getter
@Setter
@ToString
@ApiModel(description = "工人信息响应类")
public class WorkerInfoDTO implements Serializable {

    private static final long serialVersionUID = 8902324965999561267L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;
    
    @ApiModelProperty(value = "工种")
    private String workType;
    
    @ApiModelProperty(value = "角色ID")
    private Long roleId;
    
    @ApiModelProperty(value = "性别 0-女 1-男")
    private Byte gender;
    
    @ApiModelProperty(value = "联系方式")
    private String contact;
    
    @ApiModelProperty(value = "工号")
    private String workerId;
    
    @ApiModelProperty(value = "身份证号")
    private String idCard;
    
    @ApiModelProperty(value = "紧急联系人")
    private String emergencyContact;

    @ApiModelProperty(value = "状态 0-禁用 1-启用")
    private Byte status;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;
}