package com.worker.biz.constants.attendance;

import com.worker.common.constant.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * 考勤接口响应码表
 *
 * @date: 2023/11/8 15:37
 */
@Getter
@Setter
public class AttendanceResponseStatus {
    public static final ResponseStatus WORKER_NOT_EXIST = new ResponseStatus(5500, "工人不存在");
    public static final ResponseStatus START_TIME_LATE_END_TIME = new ResponseStatus(5501, "请假开始时间不能晚于结束时间");
    public static final ResponseStatus ATTEND_FAIL = new ResponseStatus(5502, "申请请假失败");
    public static final ResponseStatus ATTEND_TYPE_ERROR = new ResponseStatus(5503, "打卡类型不正确");
    public static final ResponseStatus ATTEND_REPEAT = new ResponseStatus(5504, "今天已经打过卡了");
    public static final ResponseStatus LEAVE_FAIL = new ResponseStatus(5505, "打卡失败");
    public static final ResponseStatus CANNOT_CLOCK_IN = new ResponseStatus(5506, "今天已经申请了请假，不能打卡");
    public static final ResponseStatus CLOCK_IN_FORBID_OTHERS = new ResponseStatus(5507, "非本人不能打卡");
    public static final ResponseStatus LEAVE_FORBID_OTHERS = new ResponseStatus(5508, "非本人不能请假");
    public static final ResponseStatus ALREADY_APPLIED_TODAY = new ResponseStatus(5509, "已经请过假，请勿重复请假");
}
