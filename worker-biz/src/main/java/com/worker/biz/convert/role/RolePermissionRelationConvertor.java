package com.worker.biz.convert.role;

import com.worker.client.response.permisiion.PermissionBaseDTO;
import com.worker.common.utils.ThreadLocalUtil;
import com.worker.infra.dataobject.permission.PermissionDO;
import com.worker.infra.dataobject.role.RolePermissionRelationDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Date;
import java.util.List;

/**
 * 角色权限关联关系模型转换
 *
 *  @author
 * @date: 2023/11/8 16:48
 */
@Mapper(componentModel = "spring")
public interface RolePermissionRelationConvertor {

    @Mappings({
            @Mapping(source = "id", target = "adminRoleId"),
            @Mapping(source = "permissionId", target = "adminResourceId"),
            @Mapping(target = "creator", expression = "java(getCreator())"),
            @Mapping(target = "createdTime", expression = "java(getDate())")
    })
    RolePermissionRelationDO convertRequestToPermissionDO(Long id, Long permissionId);

    default Long getCreator() {
        return ThreadLocalUtil.getAdminUserId();
    }

    default Date getDate() {
        return new Date();
    }

    List<PermissionBaseDTO> convertPermissionDOToDTO(List<PermissionDO> permissionList);
}
