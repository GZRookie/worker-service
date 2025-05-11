package com.worker.biz.convert.salary;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.worker.client.request.salary.SalaryRecordPageRequest;
import com.worker.client.response.salary.SalaryRecordPageDTO;
import com.worker.common.base.object.BasePage;
import com.worker.infra.dataobject.salary.SalaryRecordPageDO;
import com.worker.infra.dataobject.salary.SalaryRecordPageQueryDO;
import com.worker.infra.enums.WorkerRoleEnum;
import org.mapstruct.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SalaryConvertor {

    default SalaryRecordPageQueryDO convertPageRequestToDO(SalaryRecordPageRequest request) {
        SalaryRecordPageQueryDO salaryRecordPageQueryDO = new SalaryRecordPageQueryDO();
        BeanUtils.copyProperties(request, salaryRecordPageQueryDO);
        return salaryRecordPageQueryDO;
    }

    default BasePage<SalaryRecordPageDTO> convertPageToDTO(IPage<SalaryRecordPageDO> page) {
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return BasePage.empty();
        }
        List<SalaryRecordPageDTO> list = convertBasePageToDTOList(page.getRecords());
        return BasePage.build(list, page.getTotal());
    }

    default List<SalaryRecordPageDTO> convertBasePageToDTOList(List<SalaryRecordPageDO> records) {
        if ( records == null ) {
            return null;
        }

        List<SalaryRecordPageDTO> list = new ArrayList<>( records.size() );
        int i = 1;
        for ( SalaryRecordPageDO salaryRecordPageDO : records ) {
            list.add( salaryRecordPageDOToSalaryRecordPageDTO( salaryRecordPageDO, i ) );
            i++;
        }

        return list;
    }

    default SalaryRecordPageDTO salaryRecordPageDOToSalaryRecordPageDTO(SalaryRecordPageDO salaryRecordPageDO, int i) {
        if ( salaryRecordPageDO == null ) {
            return null;
        }

        SalaryRecordPageDTO salaryRecordPageDTO = new SalaryRecordPageDTO();
        BeanUtils.copyProperties( salaryRecordPageDO, salaryRecordPageDTO );
        BigDecimal dailyWage = WorkerRoleEnum.getSalaryByRoleId(salaryRecordPageDO.getRoleId());
        salaryRecordPageDTO.setDailyWage(dailyWage);
        salaryRecordPageDTO.setTotalSalary(dailyWage.multiply(new BigDecimal(salaryRecordPageDO.getWorkDays()))
                .setScale(2, BigDecimal.ROUND_HALF_UP));
        salaryRecordPageDTO.setNum(i);
        return salaryRecordPageDTO;
    }
}
