package com.worker.biz.manager.worker;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.worker.biz.convert.worker.WorkerInfoConvertor;
import com.worker.client.request.worker.WorkerEnableRequest;
import com.worker.client.request.worker.WorkerPageRequest;
import com.worker.client.request.worker.WorkerRequest;
import com.worker.client.response.worker.WorkerInfoDTO;
import com.worker.client.response.worker.WorkerPageDTO;
import com.worker.client.response.worker.WorkerRoleDTO;
import com.worker.common.base.exception.BizException;
import com.worker.common.base.object.BasePage;
import com.worker.infra.dao.role.RoleDao;
import com.worker.infra.dao.user.AdminUserDao;
import com.worker.infra.dao.worker.WorkerInfoDao;
import com.worker.infra.dataobject.role.RoleDO;
import com.worker.infra.dataobject.user.AdminUserInfoDO;
import com.worker.infra.dataobject.worker.WorkerInfoDO;
import com.worker.infra.dataobject.worker.WorkerPageQueryDO;
import com.worker.infra.enums.WorkerRoleEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.worker.biz.constants.role.RoleResponseStatus.ROLE_IS_NOT_EXIST;
import static com.worker.biz.constants.worker.WorkerResponseStatus.*;

/**
 * 工人信息Manager
 *
 *  @author
 * @date: 2023/11/3 11:35
 */
@Component
public class WorkerInfoManager {

    @Resource
    private WorkerInfoDao workerInfoDao;
    @Resource
    private AdminUserDao adminUserDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private WorkerInfoConvertor workerInfoConvertor;

    /**
     * 工人信息分页查询
     *
     * @param request 分页请求
     * @return 分页数据
     */
    public BasePage<WorkerPageDTO> pageWorkerInfo(WorkerPageRequest request) {
        WorkerPageQueryDO workerPageQueryDO = workerInfoConvertor.convertPageRequestToDO(request);
        IPage<WorkerInfoDO> page = workerInfoDao.pageWorkerInfo(workerPageQueryDO);
        return workerInfoConvertor.convertPageToDTO(page);
    }

    /**
     * 获取员工角色列表
     *
     * @return 员工角色列表
     */
    public List<WorkerRoleDTO> getWorkerRoleList() {
        List<Long> allRoleIds = WorkerRoleEnum.getAllRoleIds();
        List<RoleDO> roleDOList = roleDao.queryRoleByIds(allRoleIds);
        return roleDOList.stream()
                .map(role -> {
                    WorkerRoleDTO dto = new WorkerRoleDTO();
                    dto.setId(role.getId());
                    dto.setName(role.getRoleName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * 新增工人信息
     *
     * @param request 工人信息请求
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean addWorkerInfo(WorkerRequest request) {
        if(!WorkerRoleEnum.judgeWorkerRole(request.getRoleId())) {
            throw new BizException(NOT_WORKER_ROLE);
        }
        // 新增账号
        AdminUserInfoDO adminUserInfoDO = adminUserDao.queryAdminUserInfoByPhoneNum(request.getPhoneNum());
        if(Objects.isNull(adminUserInfoDO)) {
            WorkerInfoDO existWorkerInfoDO = workerInfoDao.queryWorkerInfoByPhoneNum(request.getPhoneNum());
            if(!Objects.isNull(existWorkerInfoDO)) {
                throw new BizException(WORKER_PHONE_NUM_EXIST);
            }
            adminUserInfoDO = workerInfoConvertor.convertAddRequestToAdminUserDO(request);
            adminUserDao.addAdminUser(adminUserInfoDO);
        } else {
            throw new BizException(WORKER_PHONE_NUM_EXIST);
        }

        // 新增工人信息
        if(Objects.isNull(request.getRoleId())) {
            throw new BizException(WORKER_ROLE_ID_IS_NULL);
        }

        RoleDO roleDO = roleDao.queryRoleById(request.getRoleId());
        if(Objects.isNull(roleDO)) {
            throw new BizException(ROLE_IS_NOT_EXIST);
        }

        WorkerInfoDO workerInfoDO = workerInfoConvertor.convertAddRequestToDO(request, roleDO.getRoleName(), adminUserInfoDO.getId());
        workerInfoDao.addWorkerInfo(workerInfoDO);

        return true;
    }

    /**
     * 编辑工人信息
     *
     * @param request 工人信息请求
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean editWorkerInfo(WorkerRequest request) {
        if(!WorkerRoleEnum.judgeWorkerRole(request.getRoleId())) {
            throw new BizException(NOT_WORKER_ROLE);
        }
        // 校验工人信息是否存在
        WorkerInfoDO workerInfoDO = workerInfoDao.queryWorkerInfoById(request.getId());
        if (Objects.isNull(workerInfoDO)) {
            throw new BizException(WORKER_NOT_EXIST);
        }

        // 校验账号是否已存在（排除自己）
        if (StringUtils.isNotEmpty(request.getPhoneNum())) {
            WorkerInfoDO existWorker = workerInfoDao.queryEditWorkerInfoByPhoneNum(request.getPhoneNum(), request.getId());
            AdminUserInfoDO existAdminUserInfoDO = adminUserDao.queryEditAdminUserInfoByPhoneNum(request.getPhoneNum(), workerInfoDO.getSysUserId());
            if (Objects.nonNull(existWorker) || Objects.nonNull(existAdminUserInfoDO)) {
                throw new BizException(PHONE_NUM_EXIST);
            }

            // 如果手机号更改了，也需要更新账号信息
            if(StringUtils.equals(workerInfoDO.getPhoneNum(), request.getPhoneNum())) {
                AdminUserInfoDO adminUserInfoDO = adminUserDao.queryAdminUserInfoByPhoneNum(workerInfoDO.getPhoneNum());
                workerInfoConvertor.convertAdminUserToDO(adminUserInfoDO, request.getPhoneNum());
                adminUserDao.updateAdminUserInfo(adminUserInfoDO);
            }
        }

        // 编辑工人信息
        RoleDO roleDO = roleDao.queryRoleById(request.getRoleId());
        workerInfoConvertor.convertEditRequestToDO(workerInfoDO, request, roleDO.getRoleName());
        workerInfoDao.editWorkerInfo(workerInfoDO);
        return true;
    }

    /**
     * 根据ID查询工人信息
     *
     * @param id 工人ID
     * @return 工人信息
     */
    public WorkerInfoDTO queryWorkerInfoById(Long id) {
        // 查询工人信息
        WorkerInfoDO workerInfoDO = workerInfoDao.queryWorkerInfoById(id);
        if (Objects.isNull(workerInfoDO)) {
            throw new BizException(WORKER_NOT_EXIST);
        }

        return workerInfoConvertor.convertWorkerInfoDoToDTO(workerInfoDO);
    }

    /**
     * 删除工人信息
     *
     * @param id 工人ID
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteWorkerInfo(Long id) {
        // 校验工人信息是否存在
        WorkerInfoDO workerInfoDO = workerInfoDao.queryWorkerInfoById(id);
        if (Objects.isNull(workerInfoDO)) {
            throw new BizException(WORKER_NOT_EXIST);
        }

        // 逻辑删除
        workerInfoConvertor.convertDeleteToDO(workerInfoDO);
        workerInfoDao.editWorkerInfo(workerInfoDO);
        AdminUserInfoDO adminUserInfoDO = adminUserDao.queryAdminUserInfoByPhoneNum(workerInfoDO.getPhoneNum());
        workerInfoConvertor.convertDeleteAdminUserToDO(adminUserInfoDO);
        adminUserDao.updateAdminUserInfo(adminUserInfoDO);
        return true;
    }


    /**
     * 启用/禁用工人信息
     *
     * @param request 启用/禁用请求
     * @return 是否成功
     */
    public Boolean enableWorkerInfo(WorkerEnableRequest request) {
        WorkerInfoDO workerInfoDO = workerInfoDao.queryWorkerInfoById(request.getId());
        if (Objects.isNull(workerInfoDO)) {
            throw new BizException(WORKER_NOT_EXIST);
        }

        workerInfoConvertor.convertEnableRequestToDO(workerInfoDO, request);
        return workerInfoDao.editWorkerInfo(workerInfoDO);
    }
}
