package com.worker.biz.service.worker.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.worker.biz.service.worker.WorkerInfoService;
import com.worker.client.request.worker.WorkerEnableRequest;
import com.worker.client.request.worker.WorkerPageRequest;
import com.worker.client.request.worker.WorkerRequest;
import com.worker.client.response.worker.WorkerInfoDTO;
import com.worker.client.response.worker.WorkerPageDTO;
import com.worker.common.base.exception.BizException;
import com.worker.common.constant.ResponseStatus;
import com.worker.common.util.BeanCopierUtil;
import com.worker.infra.dao.worker.WorkerInfoDao;
import com.worker.infra.dataobject.worker.WorkerInfoDO;
import com.worker.infra.dataobject.worker.WorkerPageDO;
import com.worker.infra.dataobject.worker.WorkerPageQueryDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 工人信息Service实现类
 *
 * @date: 2023/11/10 10:00
 */
@Service
public class WorkerInfoServiceImpl implements WorkerInfoService {

    @Resource
    private WorkerInfoDao workerInfoDao;

    @Override
    public boolean addWorkerInfo(WorkerRequest request) {
        // 校验工号是否已存在
        WorkerInfoDO existWorker = workerInfoDao.queryWorkerInfoByWorkerId(request.getWorkerId());
        if (Objects.nonNull(existWorker)) {
            throw new BizException(ResponseStatus.PARAM_ERROR.getCode(), "工号已存在");
        }

        // 请求转换为DO
        WorkerInfoDO workerInfoDO = new WorkerInfoDO();
        BeanCopierUtil.copy(request, workerInfoDO);
        workerInfoDO.setStatus((byte) 1);
        workerInfoDO.setDelete((byte) 0);

        // 新增工人信息
        return workerInfoDao.addWorkerInfo(workerInfoDO);
    }

    @Override
    public boolean editWorkerInfo(WorkerRequest request) {
        // 校验工人信息是否存在
        WorkerInfoDO workerInfo = workerInfoDao.queryWorkerInfoById(request.getId());
        if (Objects.isNull(workerInfo)) {
            throw new BizException(ResponseStatus.PARAM_ERROR.getCode(), "工人信息不存在");
        }

        // 校验工号是否已存在（排除自己）
        if (StringUtils.isNotEmpty(request.getWorkerId())) {
            WorkerInfoDO existWorker = workerInfoDao.queryEditWorkerInfoByWorkerId(request.getWorkerId(), request.getId());
            if (Objects.nonNull(existWorker)) {
                throw new BizException(ResponseStatus.PARAM_ERROR.getCode(), "工号已存在");
            }
        }

        // 请求转换为DO
        WorkerInfoDO workerInfoDO = new WorkerInfoDO();
        BeanCopierUtil.copy(request, workerInfoDO);

        // 编辑工人信息
        return workerInfoDao.editWorkerInfo(workerInfoDO);
    }

    @Override
    public boolean deleteWorkerInfo(Long id) {
        // 校验工人信息是否存在
        WorkerInfoDO workerInfo = workerInfoDao.queryWorkerInfoById(id);
        if (Objects.isNull(workerInfo)) {
            throw new BizException(ResponseStatus.PARAM_ERROR.getCode(), "工人信息不存在");
        }

        // 逻辑删除
        WorkerInfoDO workerInfoDO = new WorkerInfoDO();
        workerInfoDO.setId(id);
        workerInfoDO.setDelete((byte) 1);

        return workerInfoDao.editWorkerInfo(workerInfoDO);
    }

    @Override
    public boolean enableWorkerInfo(WorkerEnableRequest request) {
        // 校验工人信息是否存在
        WorkerInfoDO workerInfo = workerInfoDao.queryWorkerInfoById(request.getId());
        if (Objects.isNull(workerInfo)) {
            throw new BizException(ResponseStatus.PARAM_ERROR.getCode(), "工人信息不存在");
        }

        // 更新状态
        WorkerInfoDO workerInfoDO = new WorkerInfoDO();
        workerInfoDO.setId(request.getId());
        workerInfoDO.setStatus(request.getEnable());

        return workerInfoDao.editWorkerInfo(workerInfoDO);
    }

    @Override
    public IPage<WorkerPageDTO> pageWorkerInfo(WorkerPageRequest request) {
        // 请求转换为DO
        WorkerPageQueryDO queryDO = new WorkerPageQueryDO();
        BeanCopierUtil.copy(request, queryDO);

        // 分页查询
        IPage<WorkerPageDO> page = workerInfoDao.pageWorkerInfo(queryDO);

        // 结果转换
        IPage<WorkerPageDTO> result = page.convert(item -> {
            WorkerPageDTO dto = new WorkerPageDTO();
            BeanCopierUtil.copy(item, dto);
            return dto;
        });

        return result;
    }

    @Override
    public WorkerInfoDTO queryWorkerInfoById(Long id) {
        // 查询工人信息
        WorkerInfoDO workerInfo = workerInfoDao.queryWorkerInfoById(id);
        if (Objects.isNull(workerInfo)) {
            return null;
        }

        // DO转换为DTO
        WorkerInfoDTO dto = new WorkerInfoDTO();
        BeanCopierUtil.copy(workerInfo, dto);

        return dto;
    }
}