package com.worker.biz.convert.user;

import com.worker.client.request.user.AdminUserPageRequest;
import com.worker.client.request.user.AdminUserRequest;
import com.worker.client.request.user.PwdLoginRequest;
import com.worker.client.response.permisiion.PermissionNodeDTO;
import com.worker.client.response.user.AdminUserPageDTO;
import com.worker.client.response.user.AdminUserPermissionInfoDTO;
import com.worker.common.base.object.BasePage;
import com.worker.common.utils.ThreadLocalUtil;
import com.worker.infra.dataobject.user.AdminUserInfoDO;
import com.worker.infra.dataobject.user.AdminUserPageDO;
import com.worker.infra.dataobject.user.AdminUserPageQueryDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.util.CollectionUtils;

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

    @Mappings({
            @Mapping(source = "phoneNum", target = "phoneNum"),
            @Mapping(source = "password", target = "password")
    })
    AdminUserInfoDO convertPwdLoginRequestToDO(PwdLoginRequest pwdLoginRequest);

    AdminUserPageQueryDO convertPageRequestToDO(AdminUserPageRequest request);

    default BasePage<AdminUserPageDTO> convertPageToDTO(IPage<AdminUserPageDO> page) {
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return BasePage.empty();
        }
        List<AdminUserPageDTO> list = convertBasePageToDTOList(page.getRecords());
        return BasePage.build(list, page.getTotal());
    }

    List<AdminUserPageDTO> convertBasePageToDTOList(List<AdminUserPageDO> records);

    @Mappings({
            @Mapping(target = "delete", constant = "0"),
            @Mapping(target = "status", constant = "1"),
            @Mapping(target = "creator", expression = "java(getCreator())"),
            @Mapping(target = "createdTime", expression = "java(getDate())")
    })
    AdminUserInfoDO convertAddRequestToAdminUserDO(AdminUserRequest request);

    default Long getCreator() {
        return ThreadLocalUtil.getAdminUserId();
    }

    default Date getDate() {
        return new Date();
    }

    @Mappings({
            @Mapping(target = "delete", constant = "0")
    })
    AdminUserInfoDO convertEditRequestToAdminUserDO(AdminUserRequest request);

    default AdminUserPermissionInfoDTO convertToAdminUserPermissionInfoDTO(List<PermissionNodeDTO> permissionNodes) {
        AdminUserPermissionInfoDTO adminUserPermissionInfoDTO = new AdminUserPermissionInfoDTO();
        adminUserPermissionInfoDTO.setId(ThreadLocalUtil.getAdminUserId());
        adminUserPermissionInfoDTO.setPermissionList(permissionNodes);
        return adminUserPermissionInfoDTO;
    }
}
