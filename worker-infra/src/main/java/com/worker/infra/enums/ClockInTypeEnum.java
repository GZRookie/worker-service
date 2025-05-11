package com.worker.infra.enums;

import lombok.Getter;

/**
 * 打卡类型枚举
 *
 *  @author
 * @date: 2023/11/3 16:53
 */
@Getter
public enum ClockInTypeEnum {

    CLOCK_IN((byte) 1, "上班"),
    CLOCK_OUT((byte) 2, "下班");

    /**
     * 编码
     */
    private final Byte value;

    /**
     * 描述
     */
    private final String desc;


    ClockInTypeEnum(Byte value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static ClockInTypeEnum getClockInTypeEnumByValue(Byte value) {
        for (ClockInTypeEnum clockInTypeEnum : ClockInTypeEnum.values()) {
            if (clockInTypeEnum.getValue().equals(value)) {
                return clockInTypeEnum;
            }
        }
        return null;
    }
}
