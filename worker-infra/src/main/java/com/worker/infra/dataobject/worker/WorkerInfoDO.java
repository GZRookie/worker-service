package com.worker.infra.dataobject.worker;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 工人信息表
 *
 * @date: 2023/11/3 14:59
 */
@Getter
@Setter
@ToString
@TableName("worker_info")
public class WorkerInfoDO implements Serializable {

    private static final long serialVersionUID = 2303373962232361566L;

    /**
     * ID（主键自增）
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    @TableField(value = "name")
    private String name;
    
    /**
     * 工种
     */
    @TableField(value = "work_type")
    private String workType;
    
    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private Long roleId;
    
    /**
     * 性别 0-女 1-男
     */
    @TableField(value = "gender")
    private Byte gender;
    
    /**
     * 联系方式
     */
    @TableField(value = "contact")
    private String contact;
    
    /**
     * 工号
     */
    @TableField(value = "worker_id")
    private String workerId;
    
    /**
     * 身份证号
     */
    @TableField(value = "id_card")
    private String idCard;
    
    /**
     * 紧急联系人
     */
    @TableField(value = "emergency_contact")
    private String emergencyContact;

    /**
     * 状态 0-禁用 1-启用
     */
    @TableField(value = "status")
    private Byte status;

    /**
     * 状态 0-未删除 1-删除
     */
    @TableField(value = "`delete`")
    private Byte delete;

    /**
     * 创建人ID
     */
    @TableField(value = "creator", fill = FieldFill.INSERT)
    private Long creator;

    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private Date createdTime;
}