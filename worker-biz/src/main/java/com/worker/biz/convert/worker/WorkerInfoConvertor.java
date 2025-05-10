package com.worker.biz.convert.worker;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.worker.biz.common.utils.NumberUtils;
import com.worker.client.request.worker.WorkerEnableRequest;
import com.worker.client.request.worker.WorkerPageRequest;
import com.worker.client.request.worker.WorkerRequest;
import com.worker.client.response.worker.WorkerInfoDTO;
import com.worker.client.response.worker.WorkerPageDTO;
import com.worker.common.base.object.BasePage;
import com.worker.common.utils.ThreadLocalUtil;
import com.worker.infra.dataobject.user.AdminUserInfoDO;
import com.worker.infra.dataobject.worker.WorkerInfoDO;
import com.worker.infra.dataobject.worker.WorkerPageQueryDO;
import com.worker.infra.enums.DeleteEnum;
import com.worker.infra.enums.StatusEnum;
import org.mapstruct.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkerInfoConvertor {

    default WorkerInfoDO convertAddRequestToDO(WorkerRequest request, String roleName, Long sysUserId) {
        WorkerInfoDO workerInfoDO = new WorkerInfoDO();
        BeanUtils.copyProperties(request, workerInfoDO);
        workerInfoDO.setSysUserId(sysUserId);
        workerInfoDO.setWorkerNo(NumberUtils.generateWorkerNumber());
        workerInfoDO.setRoleName(roleName);
        workerInfoDO.setCreator(ThreadLocalUtil.getAdminUserId());
        workerInfoDO.setCreatedTime(new Date());
        return workerInfoDO;
    }

    default WorkerPageQueryDO convertPageRequestToDO(WorkerPageRequest request) {
        WorkerPageQueryDO workerPageQueryDO = new WorkerPageQueryDO();
        BeanUtils.copyProperties(request, workerPageQueryDO);
        return workerPageQueryDO;
    }

    default BasePage<WorkerPageDTO> convertPageToDTO(IPage<WorkerInfoDO> page) {
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return BasePage.empty();
        }
        List<WorkerPageDTO> list = convertBasePageToDTOList(page.getRecords());
        return BasePage.build(list, page.getTotal());
    }

    List<WorkerPageDTO> convertBasePageToDTOList(List<WorkerInfoDO> records);

    default void convertAdminUserToDO(AdminUserInfoDO adminUserInfoDO, String phoneNum) {
        adminUserInfoDO.setPhoneNum(phoneNum);
        adminUserInfoDO.setPassword("123456");
    }

    default void convertEditRequestToDO(WorkerInfoDO workerInfoDO, WorkerRequest request, String roleName) {
        BeanUtils.copyProperties(request, workerInfoDO);
        workerInfoDO.setRoleName(roleName);
    }

    default WorkerInfoDTO convertWorkerInfoDoToDTO(WorkerInfoDO workerInfoDO) {
        WorkerInfoDTO workerInfoDTO = new WorkerInfoDTO();
        BeanUtils.copyProperties(workerInfoDO, workerInfoDTO);
        return workerInfoDTO;
    }

    default void convertDeleteToDO(WorkerInfoDO workerInfoDO) {
        workerInfoDO.setDelete(DeleteEnum.DELETE.getValue().byteValue());
    }

    default void convertDeleteAdminUserToDO(AdminUserInfoDO adminUserInfoDO) {
        adminUserInfoDO.setDelete(DeleteEnum.DELETE.getValue().byteValue());
    }

    default void convertEnableRequestToDO(WorkerInfoDO workerInfoDO, WorkerEnableRequest request) {
        workerInfoDO.setStatus(request.getStatus());
    }

    default AdminUserInfoDO convertAddRequestToAdminUserDO(WorkerRequest request) {
        AdminUserInfoDO adminUserInfoDO = new AdminUserInfoDO();
        adminUserInfoDO.setStatus(StatusEnum.ENABLED.getValue().byteValue());
        adminUserInfoDO.setDelete(DeleteEnum.EXIST.getValue().byteValue());
        adminUserInfoDO.setPhoneNum(request.getPhoneNum());
        adminUserInfoDO.setRealName(request.getName());
        adminUserInfoDO.setCreator(ThreadLocalUtil.getAdminUserId());
        adminUserInfoDO.setCreatedTime(new Date());
        return adminUserInfoDO;
    }
}
