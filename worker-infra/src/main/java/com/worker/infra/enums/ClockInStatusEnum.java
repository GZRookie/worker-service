package com.worker.infra.enums;

import lombok.Getter;

/**
 * 打卡状态枚举
 *
 *  @author
 * @date: 2023/11/3 16:53
 */
@Getter
public enum ClockInStatusEnum {

    NORMAL((byte) 1, "正常"),
    LATE((byte) 2, "迟到"),
    LEAVE((byte) 3, "早退");

    /**
     * 编码
     */
    private final Byte value;

    /**
     * 描述
     */
    private final String desc;


    ClockInStatusEnum(Byte value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static ClockInStatusEnum getClockInStatusEnumByValue(Byte value) {
        for (ClockInStatusEnum clockInStatusEnum : ClockInStatusEnum.values()) {
            if (clockInStatusEnum.getValue().equals(value)) {
                return clockInStatusEnum;
            }
        }

        return null;
    }
}
