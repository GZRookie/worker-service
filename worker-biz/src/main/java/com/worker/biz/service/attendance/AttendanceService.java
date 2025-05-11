package com.worker.biz.service.attendance;

import com.worker.client.request.attendance.ClockInRequest;
import com.worker.client.request.attendance.LeaveRequest;
import com.worker.client.request.attendance.AttendanceRecordPageRequest;
import com.worker.client.response.attendance.AttendanceRecordPageDTO;
import com.worker.common.base.object.BasePage;
import com.worker.common.base.object.Result;

/**
 * 考勤服务接口
 */
public interface AttendanceService {

    /**
     * 考勤记录分页查询
     *
     * @param request 查询请求
     * @return 考勤记录分页数据
     */
    Result<BasePage<AttendanceRecordPageDTO>> pageAttendanceRecord(AttendanceRecordPageRequest request);

    /**
     * 申请请假
     *
     * @param request 请假请求
     * @return 是否成功
     */
    Result<Boolean> applyLeave(LeaveRequest request);

    /**
     * 打卡
     *
     * @param request 打卡请求
     * @return 打卡记录
     */
    Result<Boolean> clockIn(ClockInRequest request);
}