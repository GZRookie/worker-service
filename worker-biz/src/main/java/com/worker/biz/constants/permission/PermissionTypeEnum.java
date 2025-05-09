package com.worker.biz.constants.permission;

import lombok.Getter;

/**
 * 权限类型枚举
 *
 *  @author
 * @date: 2023/11/5 15:16
 */
@Getter
public enum PermissionTypeEnum {
    DIRECTORY((byte) 1,"目录"),
    MENU((byte) 2,"菜单"),
    BUTTON((byte) 3,"按钮");

    /**
     * 权限类型
     */
    private final Byte type;

    /**
     * 描述
     */
    private final String desc;

    PermissionTypeEnum(Byte type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
