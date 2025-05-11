package com.worker.infra.dao.attendance;

import com.worker.infra.dataobject.attendance.LeaveRecordDO;

import java.util.Date;
import java.util.List;

/**
 * 请假记录DAO接口
 */
public interface LeaveRecordDao {

    /**
     * 新增请假记录
     *
     * @param leaveRecordDO 请假记录
     * @return 是否成功
     */
    boolean addLeaveRecord(LeaveRecordDO leaveRecordDO);
    
    /**
     * 查询工人某天的请假记录
     *
     * @param workerId 工人ID
     * @param date 日期
     * @return 请假记录列表
     */
    List<LeaveRecordDO> queryLeaveRecordsByWorkerIdAndDate(Long workerId, Date date);

    /**
     * 查询工人某天的请假记录
     *
     * @param workerId 工人ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 是否有请假记录
     */
    boolean existsOverlappingLeave(Long workerId, Date startTime, Date endTime);
}