package com.worker.infra.dataobject.worker;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 工人信息分页查询对象
 *
 * @date: 2023/11/9 16:04
 */
@Getter
@Setter
public class WorkerPageQueryDO implements Serializable {

    private static final long serialVersionUID = -8266495121834437512L;

    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 工种
     */
    private String workType;

    /**
     * 工号
     */
    private String workerId;

    /**
     * 启用状态 0-禁用 1-启用
     */
    private Byte status;
}