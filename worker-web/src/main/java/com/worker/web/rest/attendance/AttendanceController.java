package com.worker.web.rest.attendance;

import com.worker.biz.service.attendance.AttendanceService;
import com.worker.client.request.attendance.ClockInRequest;
import com.worker.client.request.attendance.LeaveRequest;
import com.worker.client.request.attendance.AttendanceRecordPageRequest;
import com.worker.client.response.attendance.AttendanceRecordPageDTO;
import com.worker.common.base.object.BasePage;
import com.worker.common.base.object.Result;
import com.worker.web.common.anno.CheckOperateAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 考勤管理Controller
 */
@RestController
@RequestMapping("/attendance")
@Api(tags = "考勤管理")
public class AttendanceController {

    @Resource
    private AttendanceService attendanceService;

    @CheckOperateAuth(code = "system:attendance")
    @PostMapping("/record/page")
    @ApiOperation(value = "考勤记录分页查询", notes = "考勤记录分页查询", httpMethod = "POST")
    public Result<BasePage<AttendanceRecordPageDTO>> pageAttendanceRecord(@RequestBody @Valid AttendanceRecordPageRequest request) {
        return attendanceService.pageAttendanceRecord(request);
    }

    @CheckOperateAuth(code = "system:worker")
    @PostMapping("/leave/apply")
    @ApiOperation(value = "申请请假", notes = "申请请假", httpMethod = "POST")
    public Result<Boolean> applyLeave(@RequestBody @Valid LeaveRequest request) {
        return attendanceService.applyLeave(request);
    }

    @CheckOperateAuth(code = "system:worker")
    @PostMapping("/check")
    @ApiOperation(value = "打卡", notes = "打卡", httpMethod = "POST")
    public Result<Boolean> clockIn(@RequestBody @Valid ClockInRequest request) {
        return attendanceService.clockIn(request);
    }
}