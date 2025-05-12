package com.worker.infra.dao.attendance;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.worker.infra.dataobject.attendance.AttendanceRecordPageDO;
import com.worker.infra.dataobject.attendance.AttendanceRecordPageQueryDO;
import com.worker.infra.dataobject.attendance.ClockInRecordDO;

import java.util.Date;

/**
 * 打卡记录DAO接口
 */
public interface ClockInRecordDao {

    /**
     * 查询考勤数据
     *
     * @param attendanceRecordPageQueryDO 条件
     * @return 考勤数据
     */
    IPage<AttendanceRecordPageDO> pageAttendanceRecord(AttendanceRecordPageQueryDO attendanceRecordPageQueryDO);

    /**
     * 新增打卡记录
     *
     * @param clockInRecordDO 打卡记录
     * @return 是否成功
     */
    boolean addClockInRecord(ClockInRecordDO clockInRecordDO);

    /**
     * 根据工人ID和日期查询打卡记录
     *
     * @param workerId  工人ID
     * @param clockType 打卡类型
     * @param date      日期
     * @return 打卡记录
     */
    ClockInRecordDO queryClockInRecordByWorkerIdAndDate(Long workerId, Byte clockType, Date date);

    /**
     * 根据工人ID删除打卡记录
     *
     * @param workerId 工人ID
     * @return 是否成功
     */
    boolean deleteByWorkerId(Long workerId);
}