package com.worker.biz.manager.salary;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.worker.biz.convert.salary.SalaryConvertor;
import com.worker.client.request.salary.SalaryRecordPageRequest;
import com.worker.client.request.salary.WageEditRequest;
import com.worker.client.response.salary.SalaryRecordPageDTO;
import com.worker.client.response.salary.WorkTypeWageDTO;
import com.worker.common.base.object.BasePage;
import com.worker.infra.dao.role.RoleDao;
import com.worker.infra.dao.salary.SalaryRecordDao;
import com.worker.infra.dataobject.role.RoleDO;
import com.worker.infra.dataobject.salary.SalaryRecordPageDO;
import com.worker.infra.dataobject.salary.SalaryRecordPageQueryDO;
import com.worker.infra.enums.WorkerRoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 工资管理Manager
 */
@Component
public class SalaryManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalaryManager.class);

    @Resource
    private SalaryRecordDao salaryRecordDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private SalaryConvertor salaryConvertor;

    /**
     * 工资记录分页查询
     *
     * @param request 查询请求
     * @return 工资记录分页数据
     */
    public BasePage<SalaryRecordPageDTO> pageSalaryRecord(SalaryRecordPageRequest request) {
        SalaryRecordPageQueryDO queryDO = salaryConvertor.convertPageRequestToDO(request);
        IPage<SalaryRecordPageDO> page = salaryRecordDao.pageSalaryRecord(queryDO);
        return salaryConvertor.convertPageToDTO(page);
    }

    /**
     * 查询工种工资列表
     *
     * @return 工种工资列表
     */
    public List<WorkTypeWageDTO> listWorkTypeWage() {
        List<RoleDO> roleDOList = roleDao.queryRoleByIds(WorkerRoleEnum.getAllRoleIds());
        Map<Long, String> roleMap = roleDOList.stream()
                .collect(Collectors.toMap(RoleDO::getId, RoleDO::getRoleName));
        List<WorkTypeWageDTO> list = new ArrayList<>();
        int i = 1;
        for (WorkerRoleEnum roleEnum : WorkerRoleEnum.values()) {
            WorkTypeWageDTO dto = new WorkTypeWageDTO();
            dto.setNum(i);
            dto.setRoleId(roleEnum.getRoleId());
            dto.setRoleName(roleMap.get(roleEnum.getRoleId()));
            dto.setDailyWage(new BigDecimal(roleEnum.getSalary()));
            list.add(dto);
            i++;
        }
        return list;
    }

    /**
     * 设置工种薪资
     *
     * @param request 请求
     * @return 是否成功
     */
    public Boolean editWage(WageEditRequest request) {
        return WorkerRoleEnum.setSalaryByRoleId(request.getRoleId(), request.getDailyWage());
    }
}