package com.worker.biz.constants.role;

import lombok.Getter;

/**
 * 角色请求枚举类
 *
 *  @author
 * @date: 2023/11/8 20:49
 */
@Getter
public enum RoleRequestEnum {

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

    RoleRequestEnum(Byte bizType, String desc) {
        this.bizType = bizType;
        this.desc = desc;
    }
}
