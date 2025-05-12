package com.worker.infra.dao.worker;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.worker.infra.dataobject.worker.WorkerInfoDO;
import com.worker.infra.dataobject.worker.WorkerPageQueryDO;

import java.util.List;

/**
 * 工人信息DAO接口
 *
 * @date: 2023/11/3 14:59
 */
public interface WorkerInfoDao {

    /**
     * 工人信息分页查询
     *
     * @param queryDO 查询条件
     * @return 分页数据
     */
    IPage<WorkerInfoDO> pageWorkerInfo(WorkerPageQueryDO queryDO);

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
     * 根据手机号查询工人信息（编辑时使用）
     *
     * @param phoneNum 手机号
     * @param id 工人ID
     * @return 工人信息
     */
    WorkerInfoDO queryEditWorkerInfoByPhoneNum(String phoneNum, Long id);

    /**
     * 根据ID查询工人信息
     *
     * @param id 工人ID
     * @return 工人信息
     */
    WorkerInfoDO queryWorkerInfoById(Long id);

    /**
     * 根据手机号查询工人信息
     *
     * @param phoneNum 手机号
     * @return 工人信息
     */
    WorkerInfoDO queryWorkerInfoByPhoneNum(String phoneNum);

    /**
     * 查询工人信息
     *
     * @return 工人信息
     */
    List<WorkerInfoDO> queryWorkerInfoList();

    /**
     * 根据角色Id更新角色名称
     *
     * @param roleId 角色ID
     * @param roleName 角色名称
     * @return 是否成功
     */
    boolean updateWorkerInfoByRoleId(Long roleId, String roleName);
}