package com.worker.biz.convert.attendance;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.worker.client.request.attendance.AttendanceRecordPageRequest;
import com.worker.client.request.attendance.ClockInRequest;
import com.worker.client.request.attendance.LeaveRequest;
import com.worker.client.response.attendance.AttendanceRecordPageDTO;
import com.worker.common.base.object.BasePage;
import com.worker.common.utils.ThreadLocalUtil;
import com.worker.infra.dataobject.attendance.AttendanceRecordPageDO;
import com.worker.infra.dataobject.attendance.AttendanceRecordPageQueryDO;
import com.worker.infra.dataobject.attendance.ClockInRecordDO;
import com.worker.infra.dataobject.attendance.LeaveRecordDO;
import com.worker.infra.enums.ClockInStatusEnum;
import com.worker.infra.enums.ClockInTypeEnum;
import com.worker.infra.enums.DeleteEnum;
import com.worker.infra.enums.LeaveTypeEnum;
import org.mapstruct.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.worker.infra.enums.ClockInStatusEnum.NORMAL;

/**
 * 考勤相关转换器
 */
@Mapper(componentModel = "spring")
public interface AttendanceConvertor {

    default LeaveRecordDO convertLeaveRequestToDO(LeaveRequest request) {
        LeaveRecordDO leaveRecordDO = new LeaveRecordDO();
        BeanUtils.copyProperties(request, leaveRecordDO);
        leaveRecordDO.setCreator(ThreadLocalUtil.getAdminUserId());
        leaveRecordDO.setCreatedTime(new Date());
        leaveRecordDO.setDelete(DeleteEnum.EXIST.getValue().byteValue());
        return leaveRecordDO;
    }

    default ClockInRecordDO convertClockInRequestToDO(ClockInRequest request) {
        ClockInRecordDO clockInRecordDO = new ClockInRecordDO();
        BeanUtils.copyProperties(request, clockInRecordDO);
        clockInRecordDO.setClockTime(new Date());
        clockInRecordDO.setStatus(NORMAL.getValue());
        clockInRecordDO.setCreator(ThreadLocalUtil.getAdminUserId());
        clockInRecordDO.setCreatedTime(new Date());
        clockInRecordDO.setDelete(DeleteEnum.EXIST.getValue().byteValue());
        return clockInRecordDO;
    }

    default AttendanceRecordPageQueryDO convertPageRequestToDO(AttendanceRecordPageRequest request) {
        AttendanceRecordPageQueryDO attendanceRecordPageQueryDO = new AttendanceRecordPageQueryDO();
        BeanUtils.copyProperties(request, attendanceRecordPageQueryDO);
        return attendanceRecordPageQueryDO;
    }

    default BasePage<AttendanceRecordPageDTO> convertPageToDTO(IPage<AttendanceRecordPageDO> page) {
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return BasePage.empty();
        }
        List<AttendanceRecordPageDTO> list = convertBasePageToDTOList(page.getRecords());
        return BasePage.build(list, page.getTotal());
    }

    default List<AttendanceRecordPageDTO> convertBasePageToDTOList(List<AttendanceRecordPageDO> records) {
        if ( records == null ) {
            return null;
        }

        List<AttendanceRecordPageDTO> list = new ArrayList<AttendanceRecordPageDTO>( records.size() );
        int i = 1;
        for ( AttendanceRecordPageDO attendanceRecordPageDO : records ) {
            list.add( attendanceRecordPageDOToAttendanceRecordPageDTO( attendanceRecordPageDO, i ) );
            i++;
        }

        return list;
    }

    default AttendanceRecordPageDTO attendanceRecordPageDOToAttendanceRecordPageDTO(AttendanceRecordPageDO attendanceRecordPageDO, int i) {
        if ( attendanceRecordPageDO == null ) {
            return null;
        }

        AttendanceRecordPageDTO attendanceRecordPageDTO = new AttendanceRecordPageDTO();

        attendanceRecordPageDTO.setId(attendanceRecordPageDO.getId());
        attendanceRecordPageDTO.setNum( i );
        attendanceRecordPageDTO.setWorkerId( attendanceRecordPageDO.getWorkerId() );
        attendanceRecordPageDTO.setWorkerName( attendanceRecordPageDO.getWorkerName() );
        attendanceRecordPageDTO.setClockType( attendanceRecordPageDO.getClockType() );
        ClockInTypeEnum clockInTypeEnum = ClockInTypeEnum.getClockInTypeEnumByValue(attendanceRecordPageDO.getClockType());
        attendanceRecordPageDTO.setClockTypeName(Objects.isNull(clockInTypeEnum) ? "" : clockInTypeEnum.getDesc());
        attendanceRecordPageDTO.setClockStatus( attendanceRecordPageDO.getClockStatus() );
        ClockInStatusEnum clockInStatusEnum = ClockInStatusEnum.getClockInStatusEnumByValue(attendanceRecordPageDO.getClockStatus());
        attendanceRecordPageDTO.setClockStatusName(Objects.isNull(clockInStatusEnum) ? "" : clockInStatusEnum.getDesc());
        attendanceRecordPageDTO.setLeaveId( Objects.isNull(attendanceRecordPageDO.getLeaveId()) ? null : attendanceRecordPageDO.getLeaveId() );
        attendanceRecordPageDTO.setLeaveType( Objects.isNull(attendanceRecordPageDO.getLeaveType()) ? null : attendanceRecordPageDO.getLeaveType() );
        LeaveTypeEnum leaveTypeEnum = LeaveTypeEnum.getLeaveTypeEnumByValue(attendanceRecordPageDO.getLeaveType());
        attendanceRecordPageDTO.setLeaveTypeName(Objects.isNull(leaveTypeEnum) ? "" : leaveTypeEnum.getDesc());
        attendanceRecordPageDTO.setClockTime( attendanceRecordPageDO.getClockTime());
        attendanceRecordPageDTO.setLeaveStartTime( Objects.isNull(attendanceRecordPageDO.getLeaveStartTime()) ? null : attendanceRecordPageDO.getLeaveStartTime() );
        attendanceRecordPageDTO.setLeaveEndTime( Objects.isNull(attendanceRecordPageDO.getLeaveEndTime()) ? null : attendanceRecordPageDO.getLeaveEndTime() );
        attendanceRecordPageDTO.setLeaveReason( Objects.isNull(attendanceRecordPageDO.getLeaveReason()) ? null : attendanceRecordPageDO.getLeaveReason() );
        attendanceRecordPageDTO.setRemark( Objects.isNull(attendanceRecordPageDO.getRemark()) ? null : attendanceRecordPageDO.getRemark() );

        return attendanceRecordPageDTO;
    }
}