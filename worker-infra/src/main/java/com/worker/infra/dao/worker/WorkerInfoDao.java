package com.worker.infra.dao.worker;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.worker.infra.dataobject.worker.WorkerInfoDO;
import com.worker.infra.dataobject.worker.WorkerPageQueryDO;
import com.worker.infra.dataobject.worker.WorkerPageDO;

/**
 * 工人信息DAO接口
 *
 * @date: 2023/11/3 14:59
 */
public interface WorkerInfoDao {

    /**
     * 新增工人信息
     *
     * @param workerInfoDO 工人信息
     * @return 是否成功
     */
    boolean addWorkerInfo(WorkerInfoDO workerInfoDO);

    /**
     * 编辑工人信息
     *
     * @param workerInfoDO 工人信息
     * @return 是否成功
     */
    boolean editWorkerInfo(WorkerInfoDO workerInfoDO);

    /**
     * 根据工号查询工人信息
     *
     * @param workerId 工号
     * @return 工人信息
     */
    WorkerInfoDO queryWorkerInfoByWorkerId(String workerId);

    /**
     * 根据工号查询工人信息（编辑时使用）
     *
     * @param workerId 工号
     * @param id 工人ID
     * @return 工人信息
     */
    WorkerInfoDO queryEditWorkerInfoByWorkerId(String workerId, Long id);

    /**
     * 工人信息分页查询
     *
     * @param queryDO 查询条件
     * @return 分页数据
     */
    IPage<WorkerPageDO> pageWorkerInfo(WorkerPageQueryDO queryDO);

    /**
     * 根据ID查询工人信息
     *
     * @param id 工人ID
     * @return 工人信息
     */
    WorkerInfoDO queryWorkerInfoById(Long id);
    
    /**
     * 删除工人信息（逻辑删除）
     *
     * @param id 工人ID
     * @return 是否成功
     */
    boolean deleteWorkerInfo(Long id);
}