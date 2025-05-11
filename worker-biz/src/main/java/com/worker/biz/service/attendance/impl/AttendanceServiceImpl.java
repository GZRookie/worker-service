package com.worker.biz.service.attendance.impl;

import com.worker.biz.manager.attendance.AttendanceManager;
import com.worker.biz.service.attendance.AttendanceService;
import com.worker.client.request.attendance.ClockInRequest;
import com.worker.client.request.attendance.LeaveRequest;
import com.worker.client.request.attendance.AttendanceRecordPageRequest;
import com.worker.client.response.attendance.AttendanceRecordPageDTO;
import com.worker.common.base.object.BasePage;
import com.worker.common.base.object.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 考勤服务实现类
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttendanceServiceImpl.class);

    @Resource
    private AttendanceManager attendanceManager;

    @Override
    public Result<BasePage<AttendanceRecordPageDTO>> pageAttendanceRecord(AttendanceRecordPageRequest request) {
        return Result.success(attendanceManager.pageAttendanceRecord(request));
    }

    @Override
    public Result<Boolean> applyLeave(LeaveRequest request) {
        return Result.success(attendanceManager.applyLeave(request));
    }

    @Override
    public Result<Boolean> clockIn(ClockInRequest request) {
        return Result.success(attendanceManager.clockIn(request));
    }
}