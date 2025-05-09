package com.worker.biz.manager.permission;

import com.worker.biz.convert.permission.PermissionConvertor;
import com.worker.client.request.permission.PermissionDeleteRequest;
import com.worker.client.request.permission.PermissionPageRequest;
import com.worker.client.request.permission.PermissionRequest;
import com.worker.client.request.permission.PermissionEnableRequest;
import com.worker.client.response.permisiion.PermissionNodeDTO;
import com.worker.common.base.exception.BizException;
import com.worker.infra.dao.permission.PermissionDao;
import com.worker.infra.dataobject.permission.PermissionDO;
import com.worker.infra.enums.DeleteEnum;
import com.worker.infra.enums.StatusEnum;
import com.worker.biz.constants.permission.PermissionResponseStatus;
import com.worker.biz.constants.permission.PermissionTypeEnum;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 权限Manager
 *
 *  @author
 * @date: 2023/11/6 15:17
 */
@Component
public class PermissionManager {

    @Resource
    private PermissionDao permissionDao;
    @Resource
    private PermissionConvertor permissionConvertor;

    /**
     * 查询权限列表
     *
     * @param request 请求
     * @return 权限列表
     */
    public List<PermissionNodeDTO> listPermission(PermissionPageRequest request) {
        PermissionDO permissionDO = permissionConvertor.convertPageRequestToPermissionDO(request);
        List<PermissionDO> list = permissionDao.queryPermissionList(permissionDO);
        return permissionConvertor.convertPermissionDOToDTO(list);
    }

    private void checkParentPermission(Long parentId, Byte type) {
        // 目录校验，不能有父类
        if(PermissionTypeEnum.DIRECTORY.getType().equals(type) && !Objects.isNull(parentId)) {
            throw new BizException(PermissionResponseStatus.CANNOT_CHOSE_DIRECTORY);
        }

        if(!PermissionTypeEnum.DIRECTORY.getType().equals(type)) {
            if(Objects.isNull(parentId)) {
                throw new BizException(PermissionResponseStatus.PARENT_ID_IS_NOT_NULL);
            }
            PermissionDO permissionDO = permissionDao.queryPermissionById(parentId);
            if(Objects.isNull(permissionDO)) {
                throw new BizException(PermissionResponseStatus.PARENT_ID_NOT_EXIST);
            }

            // 菜单校验,父类必须是目录
            if(PermissionTypeEnum.MENU.getType().equals(type) && !PermissionTypeEnum.DIRECTORY.getType().equals(permissionDO.getType())) {
                throw new BizException(PermissionResponseStatus.CANNOT_CHOSE_MENU);
            }
        }

    }

    /**
     * 新增权限
     *
     * @param request 请求
     * @return 添加是否成功
     */
    public boolean addPermission(PermissionRequest request) {
        checkParentPermission(request.getParentId(), request.getType());
        boolean isExist = permissionDao.queryPermissionNameExist(request.getName());
        if(isExist) {
            throw new BizException(PermissionResponseStatus.NAME_EXIST);
        }
        PermissionDO permission = permissionConvertor.convertAddRequestToPermissionDO(request);
        return permissionDao.addPermission(permission);
    }

    /**
     * 编辑权限
     *
     * @param request 请求
     * @return 编辑是否成功
     */
    public boolean editPermission(PermissionRequest request) {
        checkParentPermission(request.getParentId(), request.getType());
        boolean isExist = permissionDao.queryEditPermissionNameExist(request.getName(), request.getId());
        if(isExist) {
            throw new BizException(PermissionResponseStatus.NAME_EXIST);
        }
        PermissionDO permission = permissionConvertor.convertEditRequestToPermissionDO(request);
        return permissionDao.updatePermission(permission);
    }

    /**
     * 删除权限
     *
     * @param request 请求
     * @return 删除是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePermission(PermissionDeleteRequest request) {
        if (CollectionUtils.isEmpty(request.getIds())) {
            return true;
        }

        for (Long id : request.getIds()) {
            PermissionDO permissionDO = new PermissionDO();
            permissionDO.setId(id);
            permissionDO.setDelete(Objects.isNull(request.getDelete()) ?
                    DeleteEnum.EXIST.getValue().byteValue() : request.getDelete());

            permissionDao.updatePermission(permissionDO);
        }
        return true;
    }

    /**
     * 启用权限
     *
     * @param request 请求
     * @return 是否成功
     */
    public boolean enablePermission(PermissionEnableRequest request) {
        PermissionDO permissionDO = new PermissionDO();
        permissionDO.setId(request.getId());
        permissionDO.setStatus(Objects.isNull(request.getEnable()) ?
                StatusEnum.ENABLED.getValue().byteValue() : request.getEnable());

        return permissionDao.updatePermission(permissionDO);
    }
}
