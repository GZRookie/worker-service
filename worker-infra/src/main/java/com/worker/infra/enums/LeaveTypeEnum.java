package com.worker.infra.enums;

import lombok.Getter;

/**
 * 请假枚举
 *
 *  @author
 * @date: 2023/11/3 16:53
 */
@Getter
public enum LeaveTypeEnum {

    PERSONAL_LEAVE(1, "事假"),
    SICK_LEAVE(2, "病假"),
    ANNUAL_LEAVE(3, "年假"),
    OTHER_LEAVE(4, "其他");

    /**
     * 编码
     */
    private final Integer value;

    /**
     * 描述
     */
    private final String desc;


    LeaveTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static LeaveTypeEnum getLeaveTypeEnumByValue(Integer value) {
        if(value == null) {
            return null;
        }
        for (LeaveTypeEnum leaveTypeEnum : LeaveTypeEnum.values()) {
            if (leaveTypeEnum.getValue().equals(value)) {
                return leaveTypeEnum;
            }
        }

        return null;
    }
}
