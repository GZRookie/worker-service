package com.worker.web.rest.salary;

import com.worker.biz.service.salary.SalaryService;
import com.worker.client.request.salary.SalaryRecordPageRequest;
import com.worker.client.request.salary.WageEditRequest;
import com.worker.client.response.salary.SalaryRecordPageDTO;
import com.worker.client.response.salary.WorkTypeWageDTO;
import com.worker.common.base.object.BasePage;
import com.worker.common.base.object.Result;
import com.worker.web.common.anno.CheckOperateAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 工资管理Controller
 */
@RestController
@RequestMapping("/salary")
@Api(tags = "工资管理")
public class SalaryController {

    @Resource
    private SalaryService salaryService;

    @CheckOperateAuth(code = "system:salary")
    @PostMapping("/record/page")
    @ApiOperation(value = "工资记录分页查询", notes = "工资记录分页查询", httpMethod = "POST")
    public Result<BasePage<SalaryRecordPageDTO>> pageSalaryRecord(@RequestBody @Valid SalaryRecordPageRequest request) {
        return salaryService.pageSalaryRecord(request);
    }

    @CheckOperateAuth(code = "system:wage")
    @GetMapping("/wage/list")
    @ApiOperation(value = "查询工种工资列表", notes = "查询工种工资列表", httpMethod = "GET")
    public Result<List<WorkTypeWageDTO>> listWorkTypeWage() {
        return salaryService.listWorkTypeWage();
    }

    @CheckOperateAuth(code = "system:wage")
    @PostMapping("/wage/edit")
    @ApiOperation(value = "设置工种薪资", notes = "设置工种薪资", httpMethod = "POST")
    public Result<Boolean> editWage(@RequestBody @Valid WageEditRequest request) {
        return salaryService.editWage(request);
    }

}