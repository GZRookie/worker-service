package com.worker.biz.manager.attendance;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.worker.biz.convert.attendance.AttendanceConvertor;
import com.worker.client.request.attendance.ClockInRequest;
import com.worker.client.request.attendance.LeaveRequest;
import com.worker.client.request.attendance.AttendanceRecordPageRequest;
import com.worker.client.response.attendance.AttendanceRecordPageDTO;
import com.worker.common.base.exception.BizException;
import com.worker.common.base.object.BasePage;
import com.worker.common.utils.ThreadLocalUtil;
import com.worker.infra.dao.attendance.ClockInRecordDao;
import com.worker.infra.dao.attendance.LeaveRecordDao;
import com.worker.infra.dao.worker.WorkerInfoDao;
import com.worker.infra.dataobject.attendance.AttendanceRecordPageDO;
import com.worker.infra.dataobject.attendance.AttendanceRecordPageQueryDO;
import com.worker.infra.dataobject.attendance.ClockInRecordDO;
import com.worker.infra.dataobject.attendance.LeaveRecordDO;
import com.worker.infra.dataobject.worker.WorkerInfoDO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.worker.biz.constants.attendance.AttendanceResponseStatus.*;
import static com.worker.infra.enums.ClockInStatusEnum.*;
import static com.worker.infra.enums.ClockInTypeEnum.CLOCK_IN;
import static com.worker.infra.enums.ClockInTypeEnum.CLOCK_OUT;

/**
 * 考勤管理Manager
 */
@Component
public class AttendanceManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttendanceManager.class);

    @Resource
    private LeaveRecordDao leaveRecordDao;
    @Resource
    private ClockInRecordDao clockInRecordDao;
    @Resource
    private WorkerInfoDao workerInfoDao;
    @Resource
    private AttendanceConvertor attendanceConvertor;

    /**
     * 考勤记录分页查询
     *
     * @param request 查询请求
     * @return 考勤记录分页数据
     */
    public BasePage<AttendanceRecordPageDTO> pageAttendanceRecord(AttendanceRecordPageRequest request) {
        AttendanceRecordPageQueryDO attendanceRecordPageQueryDO = attendanceConvertor.convertPageRequestToDO(request);
        IPage<AttendanceRecordPageDO> page = clockInRecordDao.pageAttendanceRecord(attendanceRecordPageQueryDO);
        return attendanceConvertor.convertPageToDTO(page);
    }

    /**
     * 申请请假
     *
     * @param request 请假请求
     * @return 请假记录
     */
    public Boolean applyLeave(LeaveRequest request) {
        // 校验工人是否存在
        WorkerInfoDO workerInfoDO = workerInfoDao.queryWorkerInfoById(request.getWorkerId());
        if (Objects.isNull(workerInfoDO)) {
            throw new BizException(WORKER_NOT_EXIST);
        }

        if(!Objects.equals(ThreadLocalUtil.getAdminUserId(), workerInfoDO.getSysUserId())) {
            throw new BizException(LEAVE_FORBID_OTHERS);
        }
        
        // 校验请假时间
        if (request.getStartTime().after(request.getEndTime())) {
            throw new BizException(START_TIME_LATE_END_TIME);
        }

        boolean overlap = leaveRecordDao.existsOverlappingLeave(request.getWorkerId(), request.getStartTime(), request.getEndTime());
        if (overlap) {
            throw new BizException(ALREADY_APPLIED_TODAY);
        }
        
        // 保存请假记录
        LeaveRecordDO leaveRecordDO = attendanceConvertor.convertLeaveRequestToDO(request);
        boolean success = leaveRecordDao.addLeaveRecord(leaveRecordDO);
        if(!success) {
            throw new BizException(ATTEND_FAIL);
        }
        return true;
    }

    /**
     * 打卡
     *
     * @param request 打卡请求
     * @return 打卡记录
     */
    public Boolean clockIn(ClockInRequest request) {
        // 校验工人是否存在
        WorkerInfoDO workerInfoDO = workerInfoDao.queryWorkerInfoById(request.getWorkerId());
        if (Objects.isNull(workerInfoDO)) {
            throw new BizException(WORKER_NOT_EXIST);
        }

        if(!Objects.equals(ThreadLocalUtil.getAdminUserId(), workerInfoDO.getSysUserId())) {
            throw new BizException(CLOCK_IN_FORBID_OTHERS);
        }
        
        // 校验打卡类型
        if (!CLOCK_IN.getValue().equals(request.getClockType()) && !CLOCK_OUT.getValue().equals(request.getClockType())) {
            throw new BizException(ATTEND_TYPE_ERROR);
        }
        
        // 获取当天日期
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date today = calendar.getTime();
        
        // 检查当天是否有请假记录
        List<LeaveRecordDO> leaveRecords = leaveRecordDao.queryLeaveRecordsByWorkerIdAndDate(request.getWorkerId(), today);
        if (CollectionUtils.isNotEmpty(leaveRecords)) {
            throw new BizException(CANNOT_CLOCK_IN);
        }
        
        // 检查是否已经打过卡
        ClockInRecordDO existRecord = clockInRecordDao.queryClockInRecordByWorkerIdAndDate(
                request.getWorkerId(), request.getClockType(), today);
        if (existRecord != null) {
            throw new BizException(ATTEND_REPEAT);
        }
        
        // 判断打卡状态
        ClockInRecordDO clockInRecordDO = attendanceConvertor.convertClockInRequestToDO(request);
        calendar.setTime(now);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        
        // 上班卡9点后算迟到，下班卡5点前算早退
        if (CLOCK_IN.getValue().equals(request.getClockType()) && hour >= 9) { // 上班卡
            clockInRecordDO.setStatus(LATE.getValue()); // 迟到
        } else if (CLOCK_OUT.getValue().equals(request.getClockType()) && hour < 17) { // 下班卡
            clockInRecordDO.setStatus(LEAVE.getValue()); // 早退
        }
        
        // 保存打卡记录
        boolean success = clockInRecordDao.addClockInRecord(clockInRecordDO);
        if (!success) {
            throw new BizException(LEAVE_FAIL);
        }

        return true;
    }
}