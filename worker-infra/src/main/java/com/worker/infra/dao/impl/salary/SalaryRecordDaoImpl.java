package com.worker.infra.dao.impl.salary;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.worker.common.base.exception.BizException;
import com.worker.common.base.exception.DBExceptionType;
import com.worker.common.constant.ResponseStatus;
import com.worker.common.utils.LoggerUtil;
import com.worker.infra.dao.salary.SalaryRecordDao;
import com.worker.infra.dataobject.salary.SalaryRecordPageDO;
import com.worker.infra.dataobject.salary.SalaryRecordPageQueryDO;
import com.worker.infra.mapper.salary.SalaryRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 工资记录DAO实现类
 */
@Repository
public class SalaryRecordDaoImpl implements SalaryRecordDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalaryRecordDaoImpl.class);

    @Resource
    private SalaryRecordMapper salaryRecordMapper;

    @Override
    public IPage<SalaryRecordPageDO> pageSalaryRecord(SalaryRecordPageQueryDO salaryRecordPageQueryDO) {
        IPage<SalaryRecordPageDO> page = new Page<>(salaryRecordPageQueryDO.getOffset(), salaryRecordPageQueryDO.getLimit());
        try {
            return salaryRecordMapper.selectSalaryRecord(page, salaryRecordPageQueryDO);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), salaryRecordPageQueryDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
}