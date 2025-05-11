package com.worker.biz.constants.salary;

import com.worker.common.constant.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * 工资接口响应码表
 */
@Getter
@Setter
public class SalaryResponseStatus {
    public static final ResponseStatus WORK_TYPE_NOT_EXIST = new ResponseStatus(5600, "工种不存在");
    public static final ResponseStatus WORK_TYPE_WAGE_EXIST = new ResponseStatus(5601, "该工种工资配置已存在");
    public static final ResponseStatus WORK_TYPE_WAGE_NOT_EXIST = new ResponseStatus(5602, "工种工资配置不存在");
    public static final ResponseStatus SALARY_RECORD_EXIST = new ResponseStatus(5603, "该月工资记录已存在");
    public static final ResponseStatus SALARY_RECORD_NOT_EXIST = new ResponseStatus(5604, "工资记录不存在");
    public static final ResponseStatus SALARY_CALCULATE_FAIL = new ResponseStatus(5605, "工资计算失败");
}