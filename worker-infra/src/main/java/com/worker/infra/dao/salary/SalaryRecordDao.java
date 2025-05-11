package com.worker.infra.dao.salary;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.worker.infra.dataobject.salary.SalaryRecordPageDO;
import com.worker.infra.dataobject.salary.SalaryRecordPageQueryDO;


/**
 * 工资记录DAO
 */
public interface SalaryRecordDao {

    /**
     * 工资记录分页查询
     *
     * @param queryDO 查询条件
     * @return 分页结果
     */
    IPage<SalaryRecordPageDO> pageSalaryRecord(SalaryRecordPageQueryDO queryDO);
}