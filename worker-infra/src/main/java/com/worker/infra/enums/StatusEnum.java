package com.worker.infra.enums;

import lombok.Getter;

/**
 * 用户状态枚举
 *
 *  @author
 * @date: 2023/11/3 16:53
 */
@Getter
public enum StatusEnum {

    ENABLED(1, "启用"),
    DISABLED(0, "禁用");

    /**
     * 编码
     */
    private final Integer value;

    /**
     * 描述
     */
    private final String desc;


    StatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String valueToName(Integer value) {
        for (StatusEnum val : values()) {
            if (val.getValue().equals(value)) {
                return val.getDesc();
            }
        }
        return null;
    }

    public static StatusEnum valueToEnum(Integer value) {
        for (StatusEnum val : values()) {
            if (val.getValue().equals(value)) {
                return val;
            }
        }
        return null;
    }
}
