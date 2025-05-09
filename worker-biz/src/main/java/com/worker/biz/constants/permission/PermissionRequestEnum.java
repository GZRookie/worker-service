package com.worker.biz.constants.permission;

import lombok.Getter;

/**
 * 权限请求枚举
 *
 *  @author
 * @date: 2023/11/5 14:57
 */
@Getter
public enum PermissionRequestEnum {

    ADD((byte) 1,"新增"),
    EDIT((byte) 2,"编辑");

    /**
     * 业务类型
     */
    private final Byte bizType;

    /**
     * 描述
     */
    private final String desc;

    PermissionRequestEnum(Byte bizType, String desc) {
        this.bizType = bizType;
        this.desc = desc;
    }
}
