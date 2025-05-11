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
 * 请假记录数据对象
 */
@Getter
@Setter
@TableName("leave_record")
public class LeaveRecordDO implements Serializable {

    private static final long serialVersionUID = 8834084769919831131L;

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
     * 请假类型（1-事假，2-病假，3-年假，4-其他）
     */
    @TableField(value = "leave_type")
    private Byte leaveType;

    /**
     * 请假开始时间
     */
    @TableField(value = "start_time")
    private Date startTime;

    /**
     * 请假结束时间
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 请假原因
     */
    @TableField(value = "reason")
    private String reason;

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