package com.worker.infra.dataobject.worker;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 工人信息分页结果对象
 *
 * @date: 2023/11/9 16:04
 */
@Getter
@Setter
public class WorkerPageDO implements Serializable {

    private static final long serialVersionUID = -8266495121834437512L;

    /**
     * ID
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;
    
    /**
     * 工种
     */
    private String workType;
    
    /**
     * 角色ID
     */
    private Long roleId;
    
    /**
     * 性别 0-女 1-男
     */
    private Byte gender;
    
    /**
     * 联系方式
     */
    private String contact;
    
    /**
     * 工号
     */
    private String workerId;
    
    /**
     * 身份证号
     */
    private String idCard;
    
    /**
     * 紧急联系人
     */
    private String emergencyContact;

    /**
     * 状态 0-禁用 1-启用
     */
    private Byte status;

    /**
     * 创建时间
     */
    private Date createdTime;
}