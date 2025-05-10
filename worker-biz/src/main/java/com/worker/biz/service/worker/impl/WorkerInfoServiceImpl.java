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
import com.worker.common.constant.ResponseStatus;
import com.worker.common.util.ExcelUtil;
import com.worker.infra.dao.worker.WorkerInfoDao;
import com.worker.infra.dataobject.worker.WorkerInfoDO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
        // 定义表头
        String[] headers = {"姓名", "角色名称", "性别", "联系方式", "工种", "工号", "身份证号", "紧急联系人"};
        
        // 创建工作簿
        Workbook workbook = ExcelUtil.createWorkbook("工人信息导入模板", headers);
        
        // 导出到响应
        ExcelUtil.exportToResponse(response, workbook, "工人信息导入模板");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> importWorkerInfo(MultipartFile file) {
//        if (file == null || file.isEmpty()) {
//            return Result.error(ResponseStatus.PARAM_ERROR.getCode(), "请选择要导入的文件");
//        }
//
//        // 校验文件类型
//        String fileName = file.getOriginalFilename();
//        if (fileName == null || (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls"))) {
//            return Result.error(ResponseStatus.PARAM_ERROR.getCode(), "文件格式不正确，请上传Excel文件");
//        }
//
//        try {
//            // 读取Excel数据
//            List<String[]> dataList = ExcelUtil.readExcel(file.getInputStream());
//            if (dataList.isEmpty()) {
//                return Result.error(ResponseStatus.PARAM_ERROR.getCode(), "导入的数据为空");
//            }
//
//            // 获取所有角色信息
//            List<WorkerRoleDO> roleList = workerRoleDao.getAllWorkerRoles();
//            Map<String, Long> roleNameToIdMap = roleList.stream()
//                    .collect(Collectors.toMap(WorkerRoleDO::getRoleName, WorkerRoleDO::getId, (k1, k2) -> k1));
//
//            // 校验并转换数据
//            List<WorkerInfoDO> workerList = new ArrayList<>();
//            List<String> errorMsgs = new ArrayList<>();
//            Set<String> workerIds = new HashSet<>();
//
//            for (int i = 0; i < dataList.size(); i++) {
//                String[] rowData = dataList.get(i);
//                String errorPrefix = "第" + (i + 2) + "行：";
//
//                // 校验数据完整性
//                if (rowData.length < 8) {
//                    errorMsgs.add(errorPrefix + "数据不完整");
//                    continue;
//                }
//
//                String name = rowData[0];
//                String roleName = rowData[1];
//                String gender = rowData[2];
//                String phone = rowData[3];
//                String workType = rowData[4];
//                String workerId = rowData[5];
//                String idCard = rowData[6];
//                String emergencyContact = rowData[7];
//
//                // 校验必填字段
//                if (StringUtils.isBlank(name)) {
//                    errorMsgs.add(errorPrefix + "姓名不能为空");
//                    continue;
//                }
//                if (StringUtils.isBlank(roleName)) {
//                    errorMsgs.add(errorPrefix + "角色名称不能为空");
//                    continue;
//                }
//                if (StringUtils.isBlank(workerId)) {
//                    errorMsgs.add(errorPrefix + "工号不能为空");
//                    continue;
//                }
//
//                // 校验角色是否存在
//                Long roleId = roleNameToIdMap.get(roleName);
//                if (roleId == null) {
//                    errorMsgs.add(errorPrefix + "角色名称不存在");
//                    continue;
//                }
//
//                // 校验工号是否重复
//                if (workerIds.contains(workerId)) {
//                    errorMsgs.add(errorPrefix + "工号在导入文件中重复");
//                    continue;
//                }
//                workerIds.add(workerId);
//
//                // 校验工号是否已存在于数据库
//                WorkerInfoDO existWorker = workerInfoDao.queryWorkerInfoByWorkerId(workerId);
//                if (existWorker != null) {
//                    errorMsgs.add(errorPrefix + "工号已存在于系统中");
//                    continue;
//                }
//
//                // 校验性别
//                Byte genderValue = null;
//                if (StringUtils.isNotBlank(gender)) {
//                    if ("男".equals(gender)) {
//                        genderValue = (byte) 1;
//                    } else if ("女".equals(gender)) {
//                        genderValue = (byte) 2;
//                    } else {
//                        errorMsgs.add(errorPrefix + "性别只能是男或女");
//                        continue;
//                    }
//                }
//
//                // 创建工人信息对象
//                WorkerInfoDO workerInfoDO = new WorkerInfoDO();
//                workerInfoDO.setName(name);
//                workerInfoDO.setRoleId(roleId);
//                workerInfoDO.setGender(genderValue);
//                workerInfoDO.setPhone(phone);
//                workerInfoDO.setWorkType(workType);
//                workerInfoDO.setWorkerId(workerId);
//                workerInfoDO.setIdCard(idCard);
//                workerInfoDO.setEmergencyContact(emergencyContact);
//                workerInfoDO.setStatus((byte) 1);
//                workerInfoDO.setDelete((byte) 0);
//
//                workerList.add(workerInfoDO);
//            }
//
//            // 如果有错误，返回错误信息
//            if (!errorMsgs.isEmpty()) {
//                String errorMsg = String.join("；", errorMsgs);
//                if (errorMsg.length() > 500) {
//                    errorMsg = errorMsg.substring(0, 500) + "...等" + errorMsgs.size() + "个错误";
//                }
//                return Result.error(ResponseStatus.PARAM_ERROR.getCode(), errorMsg);
//            }
//
//            // 批量保存工人信息
//            for (WorkerInfoDO workerInfoDO : workerList) {
//                workerInfoDao.addWorkerInfo(workerInfoDO);
//            }
//
//            return Result.success(true, "成功导入" + workerList.size() + "条工人信息");
//        } catch (IOException e) {
//            LOGGER.error("导入工人信息失败", e);
//            return Result.error(ResponseStatus.INTERNAL_SERVER_ERROR.getCode(), "导入失败：" + e.getMessage());
//        }
        return Result.success();
    }
}