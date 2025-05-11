package com.worker.client.response.worker;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 工人信息分页响应类
 *
 * @date: 2023/11/4 2:09
 */
@Getter
@Setter
@ToString
@ApiModel(description = "工人信息分页响应类")
public class WorkerPageDTO implements Serializable {

    private static final long serialVersionUID = 8902324965999561267L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "序号")
    private Integer num;

    @ApiModelProperty(value = "账号id")
    private Long sysUserId;

    @ApiModelProperty(value = "姓名")
    private String name;
    
    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "工种")
    private String roleName;
    
    @ApiModelProperty(value = "性别 0-女 1-男")
    private Byte gender;
    
    @ApiModelProperty(value = "联系方式")
    private String phoneNum;
    
    @ApiModelProperty(value = "工号")
    private String workerNo;
    
    @ApiModelProperty(value = "身份证号")
    private String idCard;
    
    @ApiModelProperty(value = "紧急联系人")
    private String emergencyContact;

    @ApiModelProperty(value = "状态 0-禁用 1-启用")
    private Byte status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;
}