package com.worker.infra.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 工人角色枚举
 *
 *  @author
 * @date: 2023/11/3 16:53
 */
@Getter
public enum WorkerRoleEnum {

    WORKER_ROLE_1(3L),
    WORKER_ROLE_2(4L),
    WORKER_ROLE_3(5L),
    WORKER_ROLE_4(6L),
    WORKER_ROLE_5(7L),
    WORKER_ROLE_6(8L);

    /**
     * 编码
     */
    private final Long roleId;


    WorkerRoleEnum(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取所有工人角色id
     *
     * @return 所有工人角色id
     */
    public static List<Long> getAllRoleIds() {
        return Arrays.asList(WORKER_ROLE_1.roleId, WORKER_ROLE_2.roleId, WORKER_ROLE_3.roleId,
                WORKER_ROLE_4.roleId, WORKER_ROLE_5.roleId, WORKER_ROLE_6.roleId);
    }

    /**
     * 是否是工人角色
     *
     * @param roleId 角色id
     * @return true-是 false-否
     */
    public static Boolean judgeWorkerRole(Long roleId) {
        List<Long> allRoleIds = getAllRoleIds();
        return allRoleIds.contains(roleId);
    }
}
