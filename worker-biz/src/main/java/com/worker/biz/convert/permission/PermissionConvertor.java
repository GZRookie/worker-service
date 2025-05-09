package com.worker.biz.convert.permission;

import com.worker.client.request.permission.PermissionPageRequest;
import com.worker.client.request.permission.PermissionRequest;
import com.worker.client.response.permisiion.PermissionNodeDTO;
import com.worker.common.utils.ThreadLocalUtil;
import com.worker.infra.dataobject.permission.PermissionDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Date;
import java.util.List;

/**
 * 权限模型转换
 *
 *  @author
 * @date: 2023/11/6 16:20
 */
@Mapper(componentModel = "spring")
public interface PermissionConvertor {

    List<PermissionNodeDTO> convertPermissionDOToDTO(List<PermissionDO> list);

    @Mappings({
            @Mapping(target = "delete", constant = "0"),
            @Mapping(target = "creator", expression = "java(getCreator())"),
            @Mapping(target = "createdTime", expression = "java(getDate())")
    })
    PermissionDO convertAddRequestToPermissionDO(PermissionRequest request);

    default Long getCreator() {
        return ThreadLocalUtil.getAdminUserId();
    }

    default Date getDate() {
        return new Date();
    }

    @Mappings({
            @Mapping(target = "delete", constant = "0")
    })
    PermissionDO convertEditRequestToPermissionDO(PermissionRequest request);

    PermissionDO convertPageRequestToPermissionDO(PermissionPageRequest request);
}
