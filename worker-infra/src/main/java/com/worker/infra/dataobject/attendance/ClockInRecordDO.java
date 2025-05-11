package com.worker.infra.dataobject.attendance;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 打卡记录数据对象
 */
@Getter
@Setter
@TableName("clock_in_record")
public class ClockInRecordDO implements Serializable {

    private static final long serialVersionUID = -7891593319036146330L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 工人ID
     */
    @TableField(value = "worker_id")
    private Long workerId;

    /**
     * 打卡类型（1-上班打卡，2-下班打卡）
     */
    @TableField(value = "clock_type")
    private Byte clockType;

    /**
     * 打卡时间
     */
    @TableField(value = "clock_time")
    private Date clockTime;

    /**
     * 打卡状态（1-正常，2-迟到，3-早退）
     */
    @TableField(value = "status")
    private Byte status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 创建人
     */
    @TableField(value = "creator")
    private Long creator;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updater")
    private Long updater;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    private Date updatedTime;

    /**
     * 是否删除（0-否，1-是）
     */
    @TableField(value = "`delete`")
    private Byte delete;
}