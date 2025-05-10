package com.worker.biz.manager.role;

import com.worker.biz.constants.CommonResponseConstants;
import com.worker.biz.convert.role.RoleConvertor;
import com.worker.biz.convert.role.RolePermissionRelationConvertor;
import com.worker.client.request.role.RoleDeleteRequest;
import com.worker.client.request.role.RoleEnableRequest;
import com.worker.client.request.role.RolePageRequest;
import com.worker.client.request.role.RoleRequest;
import com.worker.client.response.permisiion.PermissionBaseDTO;
import com.worker.client.response.role.RoleDTO;
import com.worker.common.base.exception.BizException;
import com.worker.common.base.object.BasePage;
import com.worker.infra.dao.permission.PermissionDao;
import com.worker.infra.dao.role.RoleDao;
import com.worker.infra.dao.role.RolePermissionRelationDao;
import com.worker.infra.dao.user.AdminUserDao;
import com.worker.infra.dao.user.AdminUserRoleRelationDao;
import com.worker.infra.dataobject.permission.PermissionDO;
import com.worker.infra.dataobject.role.RoleDO;
import com.worker.infra.dataobject.role.RolePageDO;
import com.worker.infra.dataobject.role.RolePermissionRelationDO;
import com.worker.infra.dataobject.user.AdminUserInfoDO;
import com.worker.infra.dataobject.user.AdminUserRoleRelationDO;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.worker.biz.constants.role.RoleResponseStatus;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色Manager
 *
 *  @author
 * @date: 2023/11/8 0:03
 */
@Component
public class RoleManager {

    @Resource
    private RoleDao roleDao;
    @Resource
    private RolePermissionRelationDao rolePermissionRelationDao;
    @Resource
    private AdminUserRoleRelationDao adminUserRoleRelationDao;
    @Resource
    private AdminUserDao adminUserDao;
    @Resource
    private PermissionDao permissionDao;
    @Resource
    private RoleConvertor roleConvertor;
    @Resource
    private RolePermissionRelationConvertor rolePermissionRelationConvertor;

    /**
     * 查询角色页面
     *
     * @param request 页面查询请求
     * @return 角色页面
     */
    public BasePage<RoleDTO> pageRole(RolePageRequest request) {
        RoleDO roleDO = roleConvertor.convertPageRequestToDO(request);
        IPage<RolePageDO> page = roleDao.pageRole(roleDO, request.getOffset(), request.getLimit());
        if(CollectionUtils.isNotEmpty(page.getRecords())) {
            for (RolePageDO record : page.getRecords()) {
                List<AdminUserRoleRelationDO> adminUserRoleRelationList =
                        adminUserRoleRelationDao.queryAdminUserRoleRelationsByRoleId(record.getId());
                if(CollectionUtils.isEmpty(adminUserRoleRelationList)) {
                    record.setAdminUserCount(0);
                } else {
                    List<Long> adminUserIds = adminUserRoleRelationList.stream()
                            .map(AdminUserRoleRelationDO::getAdminUserId)
                            .collect(Collectors.toList());
                    List<AdminUserInfoDO> adminUserInfoDOS = adminUserDao.queryAdminUserInfoByIds(adminUserIds);
                    record.setAdminUserCount(adminUserInfoDOS.size());
                }
            }
        }
        return roleConvertor.convertDoToPageDto(page);
    }

    /**
     * 新增角色
     *
     * @param request 新增请求
     * @return 添加是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addRole(RoleRequest request) {
        boolean isExist = roleDao.queryRoleNameExist(request.getRoleName());
        if(isExist) {
            throw new BizException(RoleResponseStatus.NAME_EXIST);
        }

        // 1.新增角色
        RoleDO roleDO = roleConvertor.convertAddRequestToRoleDO(request);
        boolean isAdd = roleDao.addRole(roleDO);
        if(!isAdd) {
            throw new BizException(CommonResponseConstants.CREATED_FAIL);
        }

        // 2.新增角色权限关联
        if(CollectionUtil.isNotEmpty(request.getPermissionIds())) {
            for (Long permissionId : request.getPermissionIds()) {
                RolePermissionRelationDO rolePermissionRelationDO =
                        rolePermissionRelationConvertor.convertRequestToPermissionDO(roleDO.getId(), permissionId);
                rolePermissionRelationDao.addRolePermissionRelation(rolePermissionRelationDO);
            }
        }

        return true;
    }

    /**
     * 编辑角色
     *
     * @param request 编辑请求
     * @return 编辑是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean editRole(RoleRequest request) {
        boolean isExist = roleDao.queryEditRoleNameExist(request.getRoleName(), request.getId());
        if(isExist) {
            throw new BizException(RoleResponseStatus.NAME_EXIST);
        }

        // 1.更新角色
        RoleDO roleDO = roleConvertor.convertEditRequestToRoleDO(request);
        boolean isEdit = roleDao.updateRole(roleDO);
        if(!isEdit) {
            throw new BizException(CommonResponseConstants.UPDATED_FAIL);
        }

        // 2.更新角色权限关联，新增的添加，不存在的删除，已存在的修改
        if(CollectionUtil.isNotEmpty(request.getPermissionIds())) {
            // 查出该角色已存在的权限ids
            List<RolePermissionRelationDO> rolePermissionRelationList =
                    rolePermissionRelationDao.queryPermissionByRoleId(request.getId());
            List<Long> existPermissionIds = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(rolePermissionRelationList)) {
                existPermissionIds = rolePermissionRelationList.stream()
                        .map(RolePermissionRelationDO::getAdminResourceId)
                        .collect(Collectors.toList());
            }
            List<Long> permissionIds = request.getPermissionIds();

            // 新增
            List<Long> finalExistPermissionIds = existPermissionIds;
            List<Long> addedPermissionIds = permissionIds.stream()
                    .filter(permissionId -> !finalExistPermissionIds.contains(permissionId))
                    .collect(Collectors.toList());
            for (Long permissionId : addedPermissionIds) {
                RolePermissionRelationDO rolePermissionRelationDO =
                        rolePermissionRelationConvertor.convertRequestToPermissionDO(roleDO.getId(), permissionId);
                rolePermissionRelationDao.addRolePermissionRelation(rolePermissionRelationDO);
            }

            // 删除
            List<Long> removedPermissionIds = finalExistPermissionIds.stream()
                    .filter(permissionId -> !permissionIds.contains(permissionId))
                    .collect(Collectors.toList());
            for (Long permissionId : removedPermissionIds) {
                RolePermissionRelationDO rolePermissionRelationDO =
                        rolePermissionRelationConvertor.convertRequestToPermissionDO(roleDO.getId(), permissionId);
                rolePermissionRelationDao.delRolePermissionRelation(rolePermissionRelationDO);
            }
        }

        return true;
    }

    /**
     * 删除角色
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRole(RoleDeleteRequest request) {
        if (CollectionUtils.isEmpty(request.getIds())) {
            return true;
        }

        List<AdminUserRoleRelationDO> adminUserRoleRelationList = adminUserRoleRelationDao.queryUserIdsByRoleIds(request.getIds());
        if(CollectionUtils.isNotEmpty(adminUserRoleRelationList)) {
            List<Long> adminUserIds = adminUserRoleRelationList.stream()
                    .map(AdminUserRoleRelationDO::getAdminUserId)
                    .collect(Collectors.toList());
            List<AdminUserInfoDO> adminUserInfoList = adminUserDao.queryAdminUserInfoByIds(adminUserIds);
            if(CollectionUtils.isNotEmpty(adminUserInfoList)) {
                throw new BizException(RoleResponseStatus.ROLE_CANNOT_DELETE);
            }
        }

        for (Long id : request.getIds()) {
            RoleDO roleDO = roleConvertor.convertDelRequestToDO(id, request);
            roleDao.updateRole(roleDO);
        }
        return true;
    }

    /**
     * 启用角色
     *
     * @param request
     * @return
     */
    public boolean enableRole(RoleEnableRequest request) {
        RoleDO roleDO = roleConvertor.convertEnableRequestToDO(request);
        return roleDao.updateRole(roleDO);
    }

    /**
     * 获取角色列表
     *
     * @return
     */
    public List<RoleDTO> list() {
        List<RoleDO> roleList = roleDao.queryRoleList();
        return roleConvertor.convertDOToDTOList(roleList);
    }

    /**
     * 根据角色查询关联的权限
     *
     * @param roleId 角色id
     * @return 权限信息
     */
    public List<PermissionBaseDTO> showRoleDetail(Long roleId) {
        List<RolePermissionRelationDO> relationList = rolePermissionRelationDao.queryPermissionByRoleId(roleId);
        if(CollectionUtils.isEmpty(relationList)) {
            return Collections.emptyList();
        }
        List<Long> permissionIds = relationList.stream()
                .map(RolePermissionRelationDO::getAdminResourceId)
                .collect(Collectors.toList());
        List<PermissionDO> permissionList = permissionDao.queryPermissionByIds(permissionIds);
        return rolePermissionRelationConvertor.convertPermissionDOToDTO(permissionList);
    }
}
