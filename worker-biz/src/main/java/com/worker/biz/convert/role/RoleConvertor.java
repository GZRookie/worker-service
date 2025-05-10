package com.worker.biz.convert.role;

import com.worker.client.request.role.RoleDeleteRequest;
import com.worker.client.request.role.RoleEnableRequest;
import com.worker.client.request.role.RolePageRequest;
import com.worker.client.request.role.RoleRequest;
import com.worker.client.response.role.RoleDTO;
import com.worker.common.base.object.BasePage;
import com.worker.common.utils.ThreadLocalUtil;
import com.worker.infra.dataobject.role.RoleDO;
import com.worker.infra.dataobject.role.RolePageDO;
import com.worker.infra.enums.DeleteEnum;
import com.worker.infra.enums.StatusEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.worker.infra.enums.WorkerRoleEnum;
import org.mapstruct.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 角色模型转换
 *
 *  @author
 * @date: 2023/11/8 14:27
 */
@Mapper(componentModel = "spring")
public interface RoleConvertor {

    default RoleDO convertPageRequestToDO(RolePageRequest request) {
        RoleDO roleDO = new RoleDO();
        roleDO.setStatus(request.getStatus());
        roleDO.setRoleName(request.getRoleName());
        return roleDO;
    }

    default BasePage<RoleDTO> convertDoToPageDto(IPage<RolePageDO> page) {
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return BasePage.empty();
        }
        List<RoleDTO> list = convertPageDOToDTOList(page.getRecords());
        return BasePage.build(list, page.getTotal());
    }

    default List<RoleDTO> convertPageDOToDTOList(List<RolePageDO> records) {
        if ( records == null ) {
            return null;
        }

        List<RoleDTO> list = new ArrayList<RoleDTO>( records.size() );
        for ( RolePageDO rolePageDO : records ) {
            list.add( rolePageDOToRoleDTO( rolePageDO ) );
        }

        return list;
    }

    default RoleDTO rolePageDOToRoleDTO(RolePageDO rolePageDO){
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(rolePageDO.getId());
        roleDTO.setRoleName(rolePageDO.getRoleName());
        roleDTO.setAdminUserCount(rolePageDO.getAdminUserCount());
        roleDTO.setStatus(rolePageDO.getStatus());
        roleDTO.setCreatedTime(rolePageDO.getCreatedTime());
        return roleDTO;
    }

    default List<RoleDTO> convertDOToDTOList(List<RoleDO> records) {
        if ( records == null ) {
            return null;
        }

        List<RoleDTO> list = new ArrayList<RoleDTO>( records.size() );
        for ( RoleDO roleDO : records ) {
            list.add( roleDOToRoleDTO( roleDO ) );
        }

        return list;
    }

    default RoleDTO roleDOToRoleDTO(RoleDO roleDO) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(roleDO.getId());
        roleDTO.setRoleName(roleDO.getRoleName());
        roleDTO.setStatus(roleDO.getStatus());
        roleDTO.setIsWorker(WorkerRoleEnum.judgeWorkerRole(roleDO.getId()));
        return roleDTO;
    }

    default RoleDO convertAddRequestToRoleDO(RoleRequest request) {
        RoleDO roleDO = new RoleDO();
        roleDO.setStatus(request.getStatus());
        roleDO.setRoleName(request.getRoleName());
        roleDO.setDelete(DeleteEnum.EXIST.getValue().byteValue());
        roleDO.setCreator(getCreator());
        roleDO.setCreatedTime(getDate());
        return roleDO;
    }

    default Long getCreator() {
        return ThreadLocalUtil.getAdminUserId();
    }

    default Date getDate() {
        return new Date();
    }

    default RoleDO convertEditRequestToRoleDO(RoleRequest request) {
        RoleDO roleDO = new RoleDO();
        roleDO.setId(request.getId());
        roleDO.setStatus(request.getStatus());
        roleDO.setRoleName(request.getRoleName());
        roleDO.setDelete(DeleteEnum.EXIST.getValue().byteValue());
        return roleDO;
    }

    default RoleDO convertDelRequestToDO(Long id, RoleDeleteRequest request) {
        RoleDO roleDO = new RoleDO();
        BeanUtils.copyProperties(request, roleDO);
        roleDO.setId(id);
        roleDO.setDelete(setDelete(request.getDelete()));
        return roleDO;
    }

    default RoleDO convertEnableRequestToDO(RoleEnableRequest request) {
        RoleDO roleDO = new RoleDO();
        BeanUtils.copyProperties(request, roleDO);
        roleDO.setStatus(setEnable(request.getEnable()));
        return roleDO;
    }

    default Byte setDelete(Byte delete) {
        return Objects.isNull(delete) ? DeleteEnum.EXIST.getValue().byteValue() : delete;
    }

    default Byte setEnable(Byte enable) {
        return Objects.isNull(enable) ? StatusEnum.ENABLED.getValue().byteValue() : enable;
    }
}
