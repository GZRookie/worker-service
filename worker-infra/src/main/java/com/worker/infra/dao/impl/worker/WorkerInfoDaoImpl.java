package com.worker.infra.dao.impl.worker;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.worker.common.base.exception.BizException;
import com.worker.common.base.exception.DBExceptionType;
import com.worker.common.constant.ResponseStatus;
import com.worker.common.utils.LoggerUtil;
import com.worker.infra.dao.worker.WorkerInfoDao;
import com.worker.infra.dataobject.worker.WorkerInfoDO;
import com.worker.infra.dataobject.worker.WorkerPageQueryDO;
import com.worker.infra.enums.DeleteEnum;
import com.worker.infra.mapper.worker.WorkerInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
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
    public IPage<WorkerInfoDO> pageWorkerInfo(WorkerPageQueryDO queryDO) {
        Page<WorkerInfoDO> page = new Page<>(queryDO.getOffset(), queryDO.getLimit());
        LambdaQueryWrapper<WorkerInfoDO> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Objects.nonNull(queryDO.getRoleId()), WorkerInfoDO::getRoleId, queryDO.getRoleId());
        wrapper.like(StringUtils.isNotEmpty(queryDO.getName()), WorkerInfoDO::getName, queryDO.getName());
        wrapper.like(StringUtils.isNotEmpty(queryDO.getWorkerNo()), WorkerInfoDO::getWorkerNo, queryDO.getWorkerNo());
        wrapper.eq(WorkerInfoDO::getDelete, DeleteEnum.EXIST.getValue().byteValue());
        wrapper.orderByDesc(WorkerInfoDO::getId);

        try {
            return workerInfoMapper.selectPage(page, wrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), queryDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
    public WorkerInfoDO queryEditWorkerInfoByPhoneNum(String phoneNum, Long id) {
        LambdaQueryWrapper<WorkerInfoDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(WorkerInfoDO::getId, id);
        wrapper.eq(StringUtils.isNotEmpty(phoneNum), WorkerInfoDO::getPhoneNum, phoneNum);
        wrapper.eq(WorkerInfoDO::getDelete, DeleteEnum.EXIST.getValue().byteValue());

        try {
            return workerInfoMapper.selectOne(wrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), phoneNum, ex);
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

    @Override
    public WorkerInfoDO queryWorkerInfoByPhoneNum(String phoneNum) {
        LambdaQueryWrapper<WorkerInfoDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WorkerInfoDO::getPhoneNum, phoneNum);

        try {
            return workerInfoMapper.selectOne(wrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), phoneNum, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<WorkerInfoDO> queryWorkerInfoList() {
        LambdaQueryWrapper<WorkerInfoDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WorkerInfoDO::getDelete, DeleteEnum.EXIST.getValue());

        try {
            return workerInfoMapper.selectList(wrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), null, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean updateWorkerInfoByRoleId(Long roleId, String roleName) {
        try {
            int rows = workerInfoMapper.updateWorkerInfoByRoleId(roleId, roleName);
            if (rows <= 0) {
                LoggerUtil.userErrorLog(LOGGER, DBExceptionType.UPDATE_EXCEPTION.getMsg(), roleId + "/" + roleName);
            }
            return rows > 0;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.UPDATE_EXCEPTION.getMsg(), roleId + "/" + roleName, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
}