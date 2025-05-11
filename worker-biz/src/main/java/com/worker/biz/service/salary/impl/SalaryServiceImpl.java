package com.worker.biz.service.salary.impl;

import com.worker.biz.manager.salary.SalaryManager;
import com.worker.biz.service.salary.SalaryService;
import com.worker.client.request.salary.SalaryRecordPageRequest;
import com.worker.client.request.salary.WageEditRequest;
import com.worker.client.response.salary.SalaryRecordPageDTO;
import com.worker.client.response.salary.WorkTypeWageDTO;
import com.worker.common.base.object.BasePage;
import com.worker.common.base.object.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 工资管理Service实现类
 */
@Service
public class SalaryServiceImpl implements SalaryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalaryServiceImpl.class);

    @Resource
    private SalaryManager salaryManager;

    @Override
    public Result<BasePage<SalaryRecordPageDTO>> pageSalaryRecord(SalaryRecordPageRequest request) {
        BasePage<SalaryRecordPageDTO> page = salaryManager.pageSalaryRecord(request);
        return Result.success(page);
    }

    @Override
    public Result<List<WorkTypeWageDTO>> listWorkTypeWage() {
        List<WorkTypeWageDTO> list = salaryManager.listWorkTypeWage();
        return Result.success(list);
    }

    @Override
    public Result<Boolean> editWage(WageEditRequest request) {
        return Result.success(salaryManager.editWage(request));
    }
}