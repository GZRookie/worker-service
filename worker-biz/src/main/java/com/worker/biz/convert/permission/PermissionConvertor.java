package com.worker.biz.convert.permission;

import com.worker.client.response.permisiion.PermissionDTO;
import com.worker.infra.dataobject.permission.PermissionDO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 权限模型转换
 *
 *  @author
 * @date: 2023/11/6 16:20
 */
@Mapper(componentModel = "spring")
public interface PermissionConvertor {

    List<PermissionDTO> convertPermissionDOToDTO(List<PermissionDO> list);
}
