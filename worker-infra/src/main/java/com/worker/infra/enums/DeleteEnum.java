package com.worker.infra.enums;

import lombok.Getter;

/**
 * 删除枚举
 *
 *  @author
 * @date: 2023/11/6 15:56
 */
@Getter
public enum DeleteEnum {
    DELETE(1, "删除"),
    EXIST(0, "非删除");

    /**
     * 编码
     */
    private final Integer value;

    /**
     * 描述
     */
    private final String desc;


    DeleteEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
