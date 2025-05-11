package com.worker.infra.enums;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工人角色枚举
 *
 *  @author
 * @date: 2023/11/3 16:53
 */
@Getter
public enum WorkerRoleEnum {

    WORKER_ROLE_1(3L, "80.00"),
    WORKER_ROLE_2(4L, "90.00"),
    WORKER_ROLE_3(5L, "100.00"),
    WORKER_ROLE_4(6L, "110.00"),
    WORKER_ROLE_5(7L, "120.00"),
    WORKER_ROLE_6(8L, "130.00");

    /**
     * 编码
     */
    private final Long roleId;

    /**
     * 日薪
     */
    private String salary;


    WorkerRoleEnum(Long roleId, String salary) {
        this.roleId = roleId;
        this.salary = salary;
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

    /**
     * 根据角色查询工资
     *
     * @param roleId 角色ID
     * @return 工资
     */
    public static BigDecimal getSalaryByRoleId(Long roleId) {
        for (WorkerRoleEnum workerRoleEnum : values()) {
            if (workerRoleEnum.roleId.equals(roleId)) {
                return new BigDecimal(workerRoleEnum.salary);
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * 根据角色设置工资
     *
     * @param roleId 角色id
     * @return 是否成功
     */
    public static Boolean setSalaryByRoleId(Long roleId, String dailyWage) {
        for (WorkerRoleEnum workerRoleEnum : values()) {
            if (workerRoleEnum.roleId.equals(roleId)) {
                workerRoleEnum.salary = dailyWage;
            }
        }
        return null;
    }
}
