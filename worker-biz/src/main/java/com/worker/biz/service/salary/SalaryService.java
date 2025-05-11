package com.worker.biz.service.salary;

import com.worker.client.request.salary.SalaryRecordPageRequest;
import com.worker.client.request.salary.WageEditRequest;
import com.worker.client.response.salary.SalaryRecordPageDTO;
import com.worker.client.response.salary.WorkTypeWageDTO;
import com.worker.common.base.object.BasePage;
import com.worker.common.base.object.Result;

import java.util.List;

/**
 * 工资管理Service
 */
public interface SalaryService {
    /**
     * 工资记录分页查询
     *
     * @param request 查询请求
     * @return 工资记录分页数据
     */
    Result<BasePage<SalaryRecordPageDTO>> pageSalaryRecord(SalaryRecordPageRequest request);

    /**
     * 查询工种工资列表
     *
     * @return 工种工资列表
     */
    Result<List<WorkTypeWageDTO>> listWorkTypeWage();

    /**
     * 设置工种薪资
     *
     * @param request 请求
     * @return 是否成功
     */
    Result<Boolean> editWage(WageEditRequest request);
}