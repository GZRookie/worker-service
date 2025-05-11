package com.worker.infra.mapper.salary;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.worker.infra.dataobject.salary.SalaryRecordDO;
import com.worker.infra.dataobject.salary.SalaryRecordPageDO;
import com.worker.infra.dataobject.salary.SalaryRecordPageQueryDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 工资记录Mapper接口
 */
@Mapper
public interface SalaryRecordMapper extends BaseMapper<SalaryRecordDO> {

    /**
     * 查询工资记录
     *
     * @param page 分页条件
     * @param salaryRecordPageQueryDO 条件
     * @return 工资记录
     */
    IPage<SalaryRecordPageDO> selectSalaryRecord(IPage<SalaryRecordPageDO> page, @Param("condition") SalaryRecordPageQueryDO salaryRecordPageQueryDO);
}