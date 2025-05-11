package com.worker.infra.dataobject.salary;

import com.worker.infra.dataobject.BasePageDO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 工资记录分页查询对象
 */
@Getter
@Setter
public class SalaryRecordPageQueryDO extends BasePageDO<SalaryRecordDO> implements Serializable {

    private static final long serialVersionUID = -872794768663143222L;

    /**
     * 工人姓名
     */
    private String workerName;
}