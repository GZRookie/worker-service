package com.worker.infra.dao.impl.attendance;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.worker.common.base.exception.BizException;
import com.worker.common.base.exception.DBExceptionType;
import com.worker.common.constant.ResponseStatus;
import com.worker.common.utils.LoggerUtil;
import com.worker.infra.dao.attendance.ClockInRecordDao;
import com.worker.infra.dataobject.attendance.AttendanceRecordPageDO;
import com.worker.infra.dataobject.attendance.AttendanceRecordPageQueryDO;
import com.worker.infra.dataobject.attendance.ClockInRecordDO;
import com.worker.infra.enums.DeleteEnum;
import com.worker.infra.mapper.attendance.ClockInRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 打卡记录DAO实现类
 */
@Repository
public class ClockInRecordDaoImpl implements ClockInRecordDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClockInRecordDaoImpl.class);

    @Resource
    private ClockInRecordMapper clockInRecordMapper;

    @Override
    public IPage<AttendanceRecordPageDO> pageAttendanceRecord(AttendanceRecordPageQueryDO attendanceRecordPageQueryDO) {
        IPage<AttendanceRecordPageDO> page = new Page<>(attendanceRecordPageQueryDO.getOffset(), attendanceRecordPageQueryDO.getLimit());
        try {
            return clockInRecordMapper.selectAttendanceRecord(page, attendanceRecordPageQueryDO);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), attendanceRecordPageQueryDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean addClockInRecord(ClockInRecordDO clockInRecordDO) {
        try {
            int rows = clockInRecordMapper.insert(clockInRecordDO);
            if (rows <= 0) {
                LoggerUtil.userErrorLog(LOGGER, DBExceptionType.INSERT_EXCEPTION.getMsg(), clockInRecordDO);
            }
            return rows > 0;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.INSERT_EXCEPTION.getMsg(), clockInRecordDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ClockInRecordDO queryClockInRecordByWorkerIdAndDate(Long workerId, Byte clockType, Date date) {
        try {
            LambdaQueryWrapper<ClockInRecordDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ClockInRecordDO::getWorkerId, workerId);
            queryWrapper.eq(ClockInRecordDO::getClockType, clockType);
            queryWrapper.eq(ClockInRecordDO::getDelete, DeleteEnum.EXIST.getValue().byteValue());
            queryWrapper.apply("DATE(clock_time) = DATE({0})", date);

            return clockInRecordMapper.selectOne(queryWrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), workerId + "/" + clockType + "/" + date, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean deleteByWorkerId(Long workerId) {
        try {
            LambdaQueryWrapper<ClockInRecordDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ClockInRecordDO::getWorkerId, workerId);
            int rows = clockInRecordMapper.delete(queryWrapper);
            if (rows <= 0) {
                LoggerUtil.userErrorLog(LOGGER, DBExceptionType.INSERT_EXCEPTION.getMsg(), workerId);
            }
            return rows > 0;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.INSERT_EXCEPTION.getMsg(), workerId, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

}