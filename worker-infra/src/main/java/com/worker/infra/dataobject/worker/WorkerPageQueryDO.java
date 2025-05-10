package com.worker.infra.dataobject.worker;

import com.worker.infra.dataobject.BasePageDO;
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
public class WorkerPageQueryDO extends BasePageDO<WorkerInfoDO> implements Serializable {

    private static final long serialVersionUID = -8266495121834437512L;

    /**
     * 姓名
     */
    private String name;

    /**
     * 工号
     */
    private String workerNo;

    /**
     * 角色id
     */
    private Long roleId;
}