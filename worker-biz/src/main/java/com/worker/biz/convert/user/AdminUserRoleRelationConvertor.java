package com.worker.biz.convert.user;

import com.worker.client.response.role.RoleBaseDTO;
import com.worker.common.utils.ThreadLocalUtil;
import com.worker.infra.dataobject.role.RoleDO;
import com.worker.infra.dataobject.user.AdminUserRoleRelationDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

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

    @Mappings({
           @Mapping(source = "userId", target = "adminUserId"),
           @Mapping(source = "roleId", target = "adminRoleId"),
            @Mapping(target = "creator", expression = "java(getCreator())"),
            @Mapping(target = "createdTime", expression = "java(getDate())")
    })
    AdminUserRoleRelationDO convertRequestToAdminUserRoleRelationDO(Long userId, Long roleId);

    default Long getCreator() {
        return ThreadLocalUtil.getAdminUserId();
    }

    default Date getDate() {
        return new Date();
    }

    List<RoleBaseDTO> convertRoleDOToDTO(List<RoleDO> roleList);
}
