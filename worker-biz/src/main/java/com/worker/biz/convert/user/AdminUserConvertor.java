package com.worker.biz.convert.user;

import com.worker.client.request.user.AdminUserPageRequest;
import com.worker.client.request.user.AdminUserRequest;
import com.worker.client.request.user.PwdLoginRequest;
import com.worker.client.response.permisiion.PermissionDTO;
import com.worker.client.response.user.AdminUserPageDTO;
import com.worker.client.response.user.AdminUserPermissionInfoDTO;
import com.worker.common.base.object.BasePage;
import com.worker.common.utils.ThreadLocalUtil;
import com.worker.infra.dataobject.user.AdminUserInfoDO;
import com.worker.infra.dataobject.user.AdminUserPageDO;
import com.worker.infra.dataobject.user.AdminUserPageQueryDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.worker.infra.enums.DeleteEnum;
import com.worker.infra.enums.StatusEnum;
import com.worker.infra.enums.WorkerRoleEnum;
import org.mapstruct.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台用户模型转换
 *
 *  @author
 * @date: 2023/11/4 16:11
 */
@Mapper(componentModel = "spring")
public interface AdminUserConvertor {

    default AdminUserInfoDO convertPwdLoginRequestToDO(PwdLoginRequest pwdLoginRequest) {
        AdminUserInfoDO adminUserInfoDO = new AdminUserInfoDO();
        adminUserInfoDO.setPhoneNum(pwdLoginRequest.getPhoneNum());
        adminUserInfoDO.setPassword(pwdLoginRequest.getPassword());
        return adminUserInfoDO;
    }

    AdminUserPageQueryDO convertPageRequestToDO(AdminUserPageRequest request);

    default BasePage<AdminUserPageDTO> convertPageToDTO(IPage<AdminUserPageDO> page) {
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return BasePage.empty();
        }
        List<AdminUserPageDTO> list = convertBasePageToDTOList(page.getRecords());
        return BasePage.build(list, page.getTotal());
    }

    default List<AdminUserPageDTO> convertBasePageToDTOList(List<AdminUserPageDO> records) {
        if ( records == null ) {
            return null;
        }

        List<AdminUserPageDTO> list = new ArrayList<AdminUserPageDTO>( records.size() );
        int i = 1;
        for ( AdminUserPageDO adminUserPageDO : records ) {
            list.add( adminUserPageDOToAdminUserPageDTO( adminUserPageDO, i ) );
            i++;
        }

        return list;
    }

    default AdminUserPageDTO adminUserPageDOToAdminUserPageDTO(AdminUserPageDO adminUserPageDO, int i) {
        if ( adminUserPageDO == null ) {
            return null;
        }

        AdminUserPageDTO adminUserPageDTO = new AdminUserPageDTO();

        adminUserPageDTO.setCreatedTime( adminUserPageDO.getCreatedTime() );
        adminUserPageDTO.setId( adminUserPageDO.getId() );
        adminUserPageDTO.setNum( i );
        adminUserPageDTO.setPhoneNum( adminUserPageDO.getPhoneNum() );
        adminUserPageDTO.setPassword( adminUserPageDO.getPassword() );
        adminUserPageDTO.setRealName( adminUserPageDO.getRealName() );
        adminUserPageDTO.setRoleId( adminUserPageDO.getRoleId() );
        adminUserPageDTO.setRoleNames( adminUserPageDO.getRoleNames() );
        adminUserPageDTO.setIsWorker(WorkerRoleEnum.judgeWorkerRole(adminUserPageDO.getRoleId()));
        adminUserPageDTO.setStatus( adminUserPageDO.getStatus() );

        return adminUserPageDTO;
    }

    default AdminUserInfoDO convertAddRequestToAdminUserDO(AdminUserRequest request) {
        AdminUserInfoDO adminUserInfoDO = new AdminUserInfoDO();
        BeanUtils.copyProperties(request, adminUserInfoDO);
        adminUserInfoDO.setDelete(DeleteEnum.EXIST.getValue().byteValue());
        adminUserInfoDO.setStatus(StatusEnum.ENABLED.getValue().byteValue());
        adminUserInfoDO.setCreator(ThreadLocalUtil.getAdminUserId());
        adminUserInfoDO.setCreatedTime(new Date());
        return adminUserInfoDO;
    }

    default AdminUserInfoDO convertEditRequestToAdminUserDO(AdminUserRequest request) {
        AdminUserInfoDO adminUserInfoDO = new AdminUserInfoDO();
        BeanUtils.copyProperties(request, adminUserInfoDO);
        adminUserInfoDO.setDelete(DeleteEnum.EXIST.getValue().byteValue());
        return adminUserInfoDO;
    }

    default AdminUserPermissionInfoDTO convertToAdminUserPermissionInfoDTO(List<PermissionDTO> permissionDTOList) {
        AdminUserPermissionInfoDTO adminUserPermissionInfoDTO = new AdminUserPermissionInfoDTO();
        adminUserPermissionInfoDTO.setId(ThreadLocalUtil.getAdminUserId());
        adminUserPermissionInfoDTO.setPermissionList(permissionDTOList);
        return adminUserPermissionInfoDTO;
    }
}
