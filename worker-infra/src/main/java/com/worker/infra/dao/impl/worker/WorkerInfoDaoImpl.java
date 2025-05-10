package com.worker.infra.dao.impl.worker;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.worker.common.base.exception.BizException;
import com.worker.common.constant.ResponseStatus;
import com.worker.common.enums.DBExceptionType;
import com.worker.common.util.LoggerUtil;
import com.worker.infra.dao.worker.WorkerInfoDao;
import com.worker.infra.dataobject.worker.WorkerInfoDO;
import com.worker.infra.dataobject.worker.WorkerPageDO;
import com.worker.infra.dataobject.worker.WorkerPageQueryDO;
import com.worker.infra.mapper.worker.WorkerInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 工人信息DAO实现类
 *
 * @date: 2023/11/3 14:59
 */
@Repository
public class WorkerInfoDaoImpl implements WorkerInfoDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerInfoDaoImpl.class);

    @Resource
    private WorkerInfoMapper workerInfoMapper;

    @Override
    public boolean addWorkerInfo(WorkerInfoDO workerInfoDO) {
        try {
            int rows = workerInfoMapper.insert(workerInfoDO);
            if (rows <= 0) {
                LoggerUtil.userErrorLog(LOGGER, DBExceptionType.INSERT_EXCEPTION.getMsg(), workerInfoDO);
            }
            return rows > 0;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.INSERT_EXCEPTION.getMsg(), workerInfoDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean editWorkerInfo(WorkerInfoDO workerInfoDO) {
        try {
            int rows = workerInfoMapper.updateById(workerInfoDO);
            if (rows <= 0) {
                LoggerUtil.userErrorLog(LOGGER, DBExceptionType.UPDATE_EXCEPTION.getMsg(), workerInfoDO);
            }
            return rows > 0;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.UPDATE_EXCEPTION.getMsg(), workerInfoDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean deleteWorkerInfo(Long id) {
        try {
            WorkerInfoDO workerInfoDO = new WorkerInfoDO();
            workerInfoDO.setId(id);
            workerInfoDO.setDelete((byte) 1);

            int rows = workerInfoMapper.updateById(workerInfoDO);
            if (rows <= 0) {
                LoggerUtil.userErrorLog(LOGGER, DBExceptionType.UPDATE_EXCEPTION.getMsg(), id);
            }
            return rows > 0;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.UPDATE_EXCEPTION.getMsg(), id, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public WorkerInfoDO queryWorkerInfoByWorkerId(String workerId) {
        LambdaQueryWrapper<WorkerInfoDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(workerId), WorkerInfoDO::getWorkerId, workerId);

        try {
            return workerInfoMapper.selectOne(wrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), workerId, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public WorkerInfoDO queryEditWorkerInfoByWorkerId(String workerId, Long id) {
        LambdaQueryWrapper<WorkerInfoDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(workerId), WorkerInfoDO::getWorkerId, workerId);
        wrapper.ne(WorkerInfoDO::getId, id);

        try {
            return workerInfoMapper.selectOne(wrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), workerId, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public IPage<WorkerPageDO> pageWorkerInfo(WorkerPageQueryDO queryDO) {
        Page<WorkerPageDO> page = new Page<>(queryDO.getPageNum(), queryDO.getPageSize());
        LambdaQueryWrapper<WorkerInfoDO> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Objects.nonNull(queryDO.getRoleId()), WorkerInfoDO::getRoleId, queryDO.getRoleId());
        wrapper.eq(Objects.nonNull(queryDO.getStatus()), WorkerInfoDO::getStatus, queryDO.getStatus());
        wrapper.like(StringUtils.isNotEmpty(queryDO.getName()), WorkerInfoDO::getName, queryDO.getName());
        wrapper.like(StringUtils.isNotEmpty(queryDO.getWorkerId()), WorkerInfoDO::getWorkerId, queryDO.getWorkerId());
        wrapper.like(StringUtils.isNotEmpty(queryDO.getWorkType()), WorkerInfoDO::getWorkType, queryDO.getWorkType());
        wrapper.eq(WorkerInfoDO::getDelete, 0);
        wrapper.orderByDesc(WorkerInfoDO::getId);

        try {
            return workerInfoMapper.selectPage(page, wrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), queryDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public WorkerInfoDO queryWorkerInfoById(Long id) {
        try {
            return workerInfoMapper.selectById(id);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), id, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
}