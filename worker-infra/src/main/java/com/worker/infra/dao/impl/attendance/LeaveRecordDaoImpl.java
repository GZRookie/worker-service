package com.worker.infra.dao.impl.attendance;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.worker.common.base.exception.BizException;
import com.worker.common.base.exception.DBExceptionType;
import com.worker.common.constant.ResponseStatus;
import com.worker.common.utils.LoggerUtil;
import com.worker.infra.dao.attendance.LeaveRecordDao;
import com.worker.infra.dataobject.attendance.LeaveRecordDO;
import com.worker.infra.enums.DeleteEnum;
import com.worker.infra.mapper.attendance.LeaveRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 请假记录DAO实现类
 */
@Repository
public class LeaveRecordDaoImpl implements LeaveRecordDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaveRecordDaoImpl.class);

    @Resource
    private LeaveRecordMapper leaveRecordMapper;

    @Override
    public boolean addLeaveRecord(LeaveRecordDO leaveRecordDO) {
        try {
            int rows = leaveRecordMapper.insert(leaveRecordDO);
            if (rows <= 0) {
                LoggerUtil.userErrorLog(LOGGER, DBExceptionType.INSERT_EXCEPTION.getMsg(), leaveRecordDO);
            }
            return rows > 0;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.INSERT_EXCEPTION.getMsg(), leaveRecordDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<LeaveRecordDO> queryLeaveRecordsByWorkerIdAndDate(Long workerId, Date date) {
        try {
            LambdaQueryWrapper<LeaveRecordDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(LeaveRecordDO::getWorkerId, workerId);
            queryWrapper.eq(LeaveRecordDO::getDelete, DeleteEnum.EXIST.getValue().byteValue());

            // 复合日期条件
            queryWrapper.and(wrapper ->
                    wrapper
                            .apply("DATE(start_time) <= DATE({0}) AND DATE(end_time) >= DATE({1})", date, date)
                            .or()
                            .apply("DATE(start_time) = DATE({0})", date)
            );

            return leaveRecordMapper.selectList(queryWrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), workerId + "/" + date, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public boolean existsOverlappingLeave(Long workerId, Date startTime, Date endTime) {
        try {
            LambdaQueryWrapper<LeaveRecordDO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(LeaveRecordDO::getWorkerId, workerId)
                    .eq(LeaveRecordDO::getDelete, DeleteEnum.EXIST.getValue().byteValue())
                    .apply("(start_time <= {0} AND end_time >= {1})", endTime, startTime);

            LeaveRecordDO conflictRecord = leaveRecordMapper.selectOne(wrapper);
            return conflictRecord != null;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), workerId, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean deleteByWorkerId(Long workerId) {
        try {
            LambdaQueryWrapper<LeaveRecordDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(LeaveRecordDO::getWorkerId, workerId);
            int rows = leaveRecordMapper.delete(queryWrapper);
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