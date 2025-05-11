package com.worker.infra.mapper.attendance;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.worker.infra.dataobject.attendance.AttendanceRecordPageDO;
import com.worker.infra.dataobject.attendance.AttendanceRecordPageQueryDO;
import com.worker.infra.dataobject.attendance.ClockInRecordDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 打卡Mapper接口
 *
 * @date: 2023/11/9 21:16
 */
@Mapper
public interface ClockInRecordMapper extends BaseMapper<ClockInRecordDO> {

    /**
     * 查询考勤数据
     *
     * @param page 分页条件
     * @param attendanceRecordPageQueryDO 条件
     * @return 考勤数据
     */
    IPage<AttendanceRecordPageDO> selectAttendanceRecord(IPage<AttendanceRecordPageDO> page, @Param("condition") AttendanceRecordPageQueryDO attendanceRecordPageQueryDO);
}