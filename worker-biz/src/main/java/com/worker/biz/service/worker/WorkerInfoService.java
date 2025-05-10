package com.worker.biz.service.worker;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.worker.client.request.worker.WorkerEnableRequest;
import com.worker.client.request.worker.WorkerPageRequest;
import com.worker.client.request.worker.WorkerRequest;
import com.worker.client.response.worker.WorkerInfoDTO;
import com.worker.client.response.worker.WorkerPageDTO;

/**
 * 工人信息Service接口
 *
 * @date: 2023/11/10 10:00
 */
public interface WorkerInfoService {

    /**
     * 新增工人信息
     *
     * @param request 工人信息请求
     * @return 是否成功
     */
    boolean addWorkerInfo(WorkerRequest request);

    /**
     * 编辑工人信息
     *
     * @param request 工人信息请求
     * @return 是否成功
     */
    boolean editWorkerInfo(WorkerRequest request);

    /**
     * 删除工人信息
     *
     * @param id 工人ID
     * @return 是否成功
     */
    boolean deleteWorkerInfo(Long id);

    /**
     * 启用/禁用工人信息
     *
     * @param request 启用/禁用请求
     * @return 是否成功
     */
    boolean enableWorkerInfo(WorkerEnableRequest request);

    /**
     * 工人信息分页查询
     *
     * @param request 分页请求
     * @return 分页数据
     */
    IPage<WorkerPageDTO> pageWorkerInfo(WorkerPageRequest request);

    /**
     * 根据ID查询工人信息
     *
     * @param id 工人ID
     * @return 工人信息
     */
    WorkerInfoDTO queryWorkerInfoById(Long id);
}
