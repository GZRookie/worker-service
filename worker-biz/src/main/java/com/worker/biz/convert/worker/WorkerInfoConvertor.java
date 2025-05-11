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
import com.worker.infra.dataobject.user.AdminUserRoleRelationDO;
import com.worker.infra.dataobject.worker.WorkerInfoDO;
import com.worker.infra.dataobject.worker.WorkerPageQueryDO;
import com.worker.infra.enums.DeleteEnum;
import com.worker.infra.enums.StatusEnum;
import org.mapstruct.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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

    default List<WorkerPageDTO> convertBasePageToDTOList(List<WorkerInfoDO> records) {
        if ( records == null ) {
            return null;
        }

        List<WorkerPageDTO> list = new ArrayList<WorkerPageDTO>( records.size() );
        int i = 1;
        for ( WorkerInfoDO workerInfoDO : records ) {
            list.add( workerInfoDOToWorkerPageDTO( workerInfoDO, i ) );
            i++;
        }

        return list;
    }

    default WorkerPageDTO workerInfoDOToWorkerPageDTO(WorkerInfoDO workerInfoDO, int i) {
        if ( workerInfoDO == null ) {
            return null;
        }

        WorkerPageDTO workerPageDTO = new WorkerPageDTO();

        workerPageDTO.setId( workerInfoDO.getId() );
        workerPageDTO.setNum( i );
        workerPageDTO.setSysUserId( workerInfoDO.getSysUserId() );
        workerPageDTO.setName( workerInfoDO.getName() );
        workerPageDTO.setRoleId( workerInfoDO.getRoleId() );
        workerPageDTO.setRoleName( workerInfoDO.getRoleName() );
        workerPageDTO.setGender( workerInfoDO.getGender() );
        workerPageDTO.setPhoneNum( workerInfoDO.getPhoneNum() );
        workerPageDTO.setWorkerNo( workerInfoDO.getWorkerNo() );
        workerPageDTO.setIdCard( workerInfoDO.getIdCard() );
        workerPageDTO.setEmergencyContact( workerInfoDO.getEmergencyContact() );
        workerPageDTO.setStatus( workerInfoDO.getStatus() );
        workerPageDTO.setCreatedTime( workerInfoDO.getCreatedTime() );

        return workerPageDTO;
    }

    default void convertAdminUserToDO(AdminUserInfoDO adminUserInfoDO, String phoneNum, String name) {
        adminUserInfoDO.setPhoneNum(phoneNum);
        adminUserInfoDO.setRealName(name);
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
        adminUserInfoDO.setPassword("123456");
        adminUserInfoDO.setRealName(request.getName());
        adminUserInfoDO.setCreator(ThreadLocalUtil.getAdminUserId());
        adminUserInfoDO.setCreatedTime(new Date());
        return adminUserInfoDO;
    }

    default WorkerInfoDO convertImportToDO(String[] rowData, Long roleId, Byte genderValue) {
        String name = rowData[0];
        String phoneNum = rowData[2];
        String roleName = rowData[3];
        String idCard = rowData[4];
        String emergencyContact = rowData[5];

        WorkerInfoDO workerInfoDO = new WorkerInfoDO();
        workerInfoDO.setName(name);
        workerInfoDO.setWorkerNo(NumberUtils.generateWorkerNumber());
        workerInfoDO.setRoleId(roleId);
        workerInfoDO.setRoleName(roleName);
        workerInfoDO.setGender(genderValue);
        workerInfoDO.setPhoneNum(phoneNum);
        workerInfoDO.setIdCard(idCard);
        workerInfoDO.setEmergencyContact(emergencyContact);
        workerInfoDO.setStatus(StatusEnum.ENABLED.getValue().byteValue());
        workerInfoDO.setDelete(DeleteEnum.EXIST.getValue().byteValue());
        workerInfoDO.setCreator(ThreadLocalUtil.getAdminUserId());
        workerInfoDO.setCreatedTime(new Date());

        return workerInfoDO;
    }

    default AdminUserInfoDO convertWorkerToAdminDO(WorkerInfoDO workerInfoDO) {
        AdminUserInfoDO adminUserInfoDO = new AdminUserInfoDO();
        adminUserInfoDO.setPhoneNum(workerInfoDO.getPhoneNum());
        adminUserInfoDO.setRealName(workerInfoDO.getName());
        adminUserInfoDO.setStatus(StatusEnum.ENABLED.getValue().byteValue());
        adminUserInfoDO.setDelete(DeleteEnum.EXIST.getValue().byteValue());
        adminUserInfoDO.setCreator(ThreadLocalUtil.getAdminUserId());
        adminUserInfoDO.setCreatedTime(new Date());
        return adminUserInfoDO;
    }

    default AdminUserRoleRelationDO convertAdminUserToRoleRelationDO(Long adminUserId, WorkerRequest request) {
        AdminUserRoleRelationDO adminUserRoleRelationDO = new AdminUserRoleRelationDO();
        adminUserRoleRelationDO.setAdminUserId(adminUserId);
        adminUserRoleRelationDO.setAdminRoleId(request.getRoleId());
        adminUserRoleRelationDO.setCreator(ThreadLocalUtil.getAdminUserId());
        adminUserRoleRelationDO.setCreatedTime(new Date());
        return adminUserRoleRelationDO;
    }
}
