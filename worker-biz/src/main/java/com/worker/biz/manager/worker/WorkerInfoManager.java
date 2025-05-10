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
import com.worker.common.util.ExcelUtil;
import com.worker.common.utils.LoggerUtil;
import com.worker.infra.dao.role.RoleDao;
import com.worker.infra.dao.user.AdminUserDao;
import com.worker.infra.dao.worker.WorkerInfoDao;
import com.worker.infra.dataobject.role.RoleDO;
import com.worker.infra.dataobject.user.AdminUserInfoDO;
import com.worker.infra.dataobject.worker.WorkerInfoDO;
import com.worker.infra.dataobject.worker.WorkerPageQueryDO;
import com.worker.infra.enums.DeleteEnum;
import com.worker.infra.enums.StatusEnum;
import com.worker.infra.enums.WorkerRoleEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerInfoManager.class);

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

    /**
     * 下载工人信息导入模板
     *
     * @param response HTTP响应
     */
    public void downloadImportTemplate(HttpServletResponse response) {
        // 定义表头
        String[] headers = {"姓名(必填)", "性别(必填)", "联系方式(必填)", "工种(必填)", "身份证号(必填)", "紧急联系人(必填)"};
        // 创建工作簿
        Workbook workbook = ExcelUtil.createWorkbook("工人信息导入模板", headers);
        // 导出到响应
        ExcelUtil.exportToResponse(response, workbook, "工人信息导入模板");
    }

    /**
     * 批量导入工人信息
     *
     * @param file Excel文件
     * @return 导入结果
     */
    public Integer importWorkerInfo(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BizException(CHOSE_FILE);
        }

        // 校验文件类型
        String fileName = file.getOriginalFilename();
        if (fileName == null || (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls"))) {
            throw new BizException(FILE_FORMAT_ERROR);
        }

        try {
            // 读取Excel数据
            List<String[]> dataList = ExcelUtil.readExcel(file.getInputStream());
            if (dataList.isEmpty()) {
                throw new BizException(DATA_IS_EMPTY);
            }

            // 获取所有角色信息
            List<RoleDO> roleDOList = roleDao.queryRoleByIds(WorkerRoleEnum.getAllRoleIds());
            Map<String, Long> roleNameToIdMap = roleDOList.stream()
                    .collect(Collectors.toMap(RoleDO::getRoleName, RoleDO::getId, (k1, k2) -> k1));

            // 获取所有手机号账号和工人信息
            List<AdminUserInfoDO> adminUserInfoDOList = adminUserDao.queryAdminUserInfoList();
            Map<String, Long> adminUserPhoneToIdMap = adminUserInfoDOList.stream()
                    .collect(Collectors.toMap(AdminUserInfoDO::getPhoneNum, AdminUserInfoDO::getId, (k1, k2) -> k1));
            List<WorkerInfoDO> workerInfoDOList = workerInfoDao.queryWorkerInfoList();
            Map<String, Long> workerPhoneToIdMap = workerInfoDOList.stream()
                    .collect(Collectors.toMap(WorkerInfoDO::getPhoneNum, WorkerInfoDO::getId, (k1, k2) -> k1));

            // 校验并转换数据
            List<WorkerInfoDO> workerList = new ArrayList<>();
            List<String> errorMsgList = new ArrayList<>();

            for (int i = 0; i < dataList.size(); i++) {
                String[] rowData = dataList.get(i);
                String errorPrefix = "第" + (i + 2) + "行：";

                // 校验数据完整性
                if (rowData.length < 6) {
                    errorMsgList.add(errorPrefix + "数据不完整");
                    continue;
                }

                String name = rowData[0];
                String gender = rowData[1];
                String phoneNum = rowData[2];
                String roleName = rowData[3];
                String idCard = rowData[4];
                String emergencyContact = rowData[5];

                // 校验必填字段
                if (StringUtils.isBlank(name)) {
                    errorMsgList.add(errorPrefix + "姓名不能为空");
                    continue;
                }

                if (StringUtils.isBlank(gender)) {
                    errorMsgList.add(errorPrefix + "性别不能为空");
                    continue;
                }

                if (StringUtils.isBlank(phoneNum)) {
                    errorMsgList.add(errorPrefix + "联系方式不能为空");
                    continue;
                }

                if (StringUtils.isBlank(roleName)) {
                    errorMsgList.add(errorPrefix + "工种不能为空");
                    continue;
                }

                if (StringUtils.isBlank(idCard)) {
                    errorMsgList.add(errorPrefix + "身份证号不能为空");
                    continue;
                }

                if (StringUtils.isBlank(emergencyContact)) {
                    errorMsgList.add(errorPrefix + "紧急联系人不能为空");
                    continue;
                }

                // 校验角色是否存在
                Long roleId = roleNameToIdMap.get(roleName);
                if (roleId == null) {
                    errorMsgList.add(errorPrefix + "工种不存在");
                    continue;
                }

                // 校验手机号是否存在
                Long adminUserId = adminUserPhoneToIdMap.get(phoneNum);
                Long workerId = workerPhoneToIdMap.get(phoneNum);
                if (adminUserId != null || workerId != null) {
                    errorMsgList.add(errorPrefix + "手机账号已存在");
                    continue;
                }

                // 校验性别
                Byte genderValue = null;
                if (StringUtils.isNotBlank(gender)) {
                    if ("男".equals(gender)) {
                        genderValue = (byte) 1;
                    } else if ("女".equals(gender)) {
                        genderValue = (byte) 2;
                    } else {
                        errorMsgList.add(errorPrefix + "性别只能是男或女");
                        continue;
                    }
                }

                // 创建工人信息对象
                WorkerInfoDO workerInfoDO = workerInfoConvertor.convertImportToDO(rowData, roleId, genderValue);
                workerList.add(workerInfoDO);
            }

            // 如果有错误，返回错误信息
            if (!errorMsgList.isEmpty()) {
                String errorMsg = String.join("；", errorMsgList);
                LoggerUtil.userInfoLog(LOGGER, "导入工人信息有错误", errorMsg);
            }

            // 批量保存工人信息
            for (WorkerInfoDO workerInfoDO : workerList) {
                // 先新增账号用户
                AdminUserInfoDO adminUserInfoDO = workerInfoConvertor.convertWorkerToAdminDO(workerInfoDO);
                adminUserDao.addAdminUser(adminUserInfoDO);
                workerInfoDO.setSysUserId(adminUserInfoDO.getId());
                workerInfoDao.addWorkerInfo(workerInfoDO);
            }

            return workerList.size();
        } catch (Exception e) {
            LoggerUtil.userErrorLog(LOGGER, "导入工人信息失败", e);
            return 0;
        }
    }
}
