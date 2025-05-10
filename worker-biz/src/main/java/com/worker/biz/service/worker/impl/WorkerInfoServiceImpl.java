package com.worker.biz.service.worker.impl;

import com.worker.biz.manager.worker.WorkerInfoManager;
import com.worker.biz.service.worker.WorkerInfoService;
import com.worker.client.request.worker.WorkerEnableRequest;
import com.worker.client.request.worker.WorkerPageRequest;
import com.worker.client.request.worker.WorkerRequest;
import com.worker.client.response.worker.WorkerInfoDTO;
import com.worker.client.response.worker.WorkerPageDTO;
import com.worker.client.response.worker.WorkerRoleDTO;
import com.worker.common.base.object.BasePage;
import com.worker.common.base.object.Result;
import com.worker.infra.dao.worker.WorkerInfoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 工人信息Service实现类
 *
 * @date: 2023/11/10 10:00
 */
@Service
public class WorkerInfoServiceImpl implements WorkerInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerInfoServiceImpl.class);

    @Resource
    private WorkerInfoManager workerInfoManager;

    @Resource
    private WorkerInfoDao workerInfoDao;

    @Override
    public Result<BasePage<WorkerPageDTO>> pageWorkerInfo(WorkerPageRequest request) {
        BasePage<WorkerPageDTO> page = workerInfoManager.pageWorkerInfo(request);
        if (Objects.isNull(page.getData())) {
            return Result.success(BasePage.empty());
        }
        return Result.success(page);
    }

    @Override
    public Result<List<WorkerRoleDTO>> getWorkerRoleList() {
        return Result.success(workerInfoManager.getWorkerRoleList());
    }

    @Override
    public Result<Boolean> addWorkerInfo(WorkerRequest request) {
        return Result.success(workerInfoManager.addWorkerInfo(request));
    }

    @Override
    public Result<Boolean> editWorkerInfo(WorkerRequest request) {
        return Result.success(workerInfoManager.editWorkerInfo(request));
    }

    @Override
    public Result<WorkerInfoDTO> queryWorkerInfoById(Long id) {
        return Result.success(workerInfoManager.queryWorkerInfoById(id));
    }

    @Override
    public Result<Boolean> deleteWorkerInfo(Long id) {
        return Result.success(workerInfoManager.deleteWorkerInfo(id));
    }

    @Override
    public Result<Boolean> enableWorkerInfo(WorkerEnableRequest request) {
        return Result.success(workerInfoManager.enableWorkerInfo(request));
    }

    @Override
    public void downloadImportTemplate(HttpServletResponse response) {
        workerInfoManager.downloadImportTemplate(response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> importWorkerInfo(MultipartFile file) {
        Integer num = workerInfoManager.importWorkerInfo(file);
        return num > 0 ? Result.success("成功导入" + num + "条工人信息") : Result.error("导入失败");
    }
}