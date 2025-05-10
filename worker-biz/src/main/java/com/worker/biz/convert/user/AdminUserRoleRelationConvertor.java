package com.worker.biz.convert.user;

import com.worker.client.response.role.RoleBaseDTO;
import com.worker.common.utils.ThreadLocalUtil;
import com.worker.infra.dataobject.role.RoleDO;
import com.worker.infra.dataobject.user.AdminUserRoleRelationDO;
import org.mapstruct.Mapper;

import java.util.Date;
import java.util.List;

/**
 * 后台用户角色关系模型转换
 *
 *  @author
 * @date: 2023/11/10 17:11
 */
@Mapper(componentModel = "spring")
public interface AdminUserRoleRelationConvertor {

    default AdminUserRoleRelationDO convertRequestToAdminUserRoleRelationDO(Long userId, Long roleId) {
        AdminUserRoleRelationDO adminUserRoleRelationDO = new AdminUserRoleRelationDO();
        adminUserRoleRelationDO.setAdminUserId(userId);
        adminUserRoleRelationDO.setAdminRoleId(roleId);
        adminUserRoleRelationDO.setCreator(getCreator());
        adminUserRoleRelationDO.setCreatedTime(getDate());
        return adminUserRoleRelationDO;
    }

    default Long getCreator() {
        return ThreadLocalUtil.getAdminUserId();
    }

    default Date getDate() {
        return new Date();
    }

    List<RoleBaseDTO> convertRoleDOToDTO(List<RoleDO> roleList);
}
