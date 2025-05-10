package com.worker.biz.manager.user;

import com.worker.biz.constants.CommonResponseConstants;
import com.worker.biz.constants.jwt.JwtBizConstants;
import com.worker.biz.constants.user.AdminUserResponseStatus;
import com.worker.biz.convert.permission.PermissionConvertor;
import com.worker.biz.convert.user.AdminUserConvertor;
import com.worker.biz.convert.user.AdminUserRoleRelationConvertor;
import com.worker.client.response.permisiion.PermissionDTO;
import com.worker.client.response.role.RoleBaseDTO;
import com.worker.client.response.user.AdminUserPageDTO;
import com.worker.client.response.user.AdminUserPermissionInfoDTO;
import com.worker.client.request.user.*;
import com.worker.common.base.exception.BizException;
import com.worker.common.base.object.BasePage;
import com.worker.common.utils.RegularUtils;
import com.worker.common.utils.ThreadLocalUtil;
import com.worker.infra.dao.permission.PermissionDao;
import com.worker.infra.dao.role.RoleDao;
import com.worker.infra.dao.role.RolePermissionRelationDao;
import com.worker.infra.dao.user.AdminUserDao;
import com.worker.infra.dao.user.AdminUserRoleRelationDao;
import com.worker.infra.dataobject.permission.PermissionDO;
import com.worker.infra.dataobject.role.RoleDO;
import com.worker.infra.dataobject.role.RolePermissionRelationDO;
import com.worker.infra.dataobject.user.AdminUserInfoDO;
import com.worker.infra.dataobject.user.AdminUserPageDO;
import com.worker.infra.dataobject.user.AdminUserPageQueryDO;
import com.worker.infra.dataobject.user.AdminUserRoleRelationDO;
import com.worker.infra.enums.DeleteEnum;
import com.worker.infra.enums.StatusEnum;
import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.worker.biz.constants.user.AdminUserBizConstants;
import com.worker.infra.enums.WorkerRoleEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.worker.common.utils.RegularUtils.PASSWORD;

/**
 * 后台用户Manager
 *
 *  @author
 * @date: 2023/11/3 11:35
 */
@Component
public class AdminUserManager {

    @Resource
    private AdminUserDao adminUserDao;
    @Resource
    private AdminUserRoleRelationDao adminUserRoleRelationDao;
    @Resource
    private RolePermissionRelationDao rolePermissionRelationDao;
    @Resource
    private PermissionDao permissionDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private AdminUserConvertor adminUserConvertor;
    @Resource
    private AdminUserRoleRelationConvertor adminUserRoleRelationConvertor;
    @Resource
    private PermissionConvertor permissionConvertor;

    /**
     * 后台用户登录
     *
     * @param pwdLoginRequest
     * @return
     */
    public Long login(PwdLoginRequest pwdLoginRequest) {

        // 校验手机号和密码
        AdminUserInfoDO adminUserInfoDO = adminUserDao.queryAdminUserInfoByPhoneNum(pwdLoginRequest.getPhoneNum());
        if(Objects.isNull(adminUserInfoDO)) {
            throw new BizException(AdminUserResponseStatus.PHONE_NUM_NOT_EXIST);
        }
        if(!StringUtils.equals(adminUserInfoDO.getPassword(), pwdLoginRequest.getPassword())) {
            throw new BizException(AdminUserResponseStatus.PASSWORD_ERROR);
        }
        if (StatusEnum.DISABLED.getValue().byteValue() == adminUserInfoDO.getStatus()) {
            throw new BizException(AdminUserResponseStatus.USER_IS_DISABLED);
        }

        // 登录前先做注销操作
        StpUtil.logout(adminUserInfoDO.getId());

        // 登录操作
        StpUtil.login(JwtBizConstants.ADMIN_USER + adminUserInfoDO.getId(), SaLoginConfig
                .setExtra(JwtBizConstants.ADMIN_USER_ID_KEY, adminUserInfoDO.getId())
                .setExtra(JwtBizConstants.PHONE_NUM_KEY, adminUserInfoDO.getPhoneNum())
                .setExtra(JwtBizConstants.IDENTITY, JwtBizConstants.ADMIN));

        return adminUserInfoDO.getId();
    }

    /**
     * 后台用户修改密码
     *
     * @param pwdLoginRequest 请求
     * @return 是否成功
     */
    public Boolean update(PwdLoginRequest pwdLoginRequest) {
        // 校验密码格式
        boolean isPassword = RegularUtils.checkParamsByRegular(pwdLoginRequest.getPassword(), PASSWORD);
        if(!isPassword) {
            throw new BizException(AdminUserResponseStatus.PASSWORD_FORMAT_ERROR);
        }

        // 校验确认密码格式
        if(StringUtils.isEmpty(pwdLoginRequest.getEnterPassword())) {
            throw new BizException(AdminUserResponseStatus.ENTER_PASSWORD_IS_EMPTY);
        }
        boolean isEnterPassword = RegularUtils.checkParamsByRegular(pwdLoginRequest.getEnterPassword(), PASSWORD);
        if(!isEnterPassword) {
            throw new BizException(AdminUserResponseStatus.ENTER_PASSWORD_FORMAT_ERROR);
        }

        // 校验确认密码是否和密码一致
        if(!StringUtils.equals(pwdLoginRequest.getPassword(), pwdLoginRequest.getPassword())) {
            throw new BizException(AdminUserResponseStatus.ENTER_PASSWORD_NOT_EQUAL);
        }

        // 修改密码
        AdminUserInfoDO adminUserInfoDO = adminUserConvertor.convertPwdLoginRequestToDO(pwdLoginRequest);
        return adminUserDao.updateAdminUserInfo(adminUserInfoDO);
    }

    /**
     * 后台用户登出
     *
     * @param pwdLogoutRequest 请求
     * @return 是否成功
     */
    public Boolean logout(PwdLogoutRequest pwdLogoutRequest) {
        try{
            StpUtil.logout(pwdLogoutRequest.getAdminUserId());
        }catch (Exception ex) {
            throw new BizException(AdminUserResponseStatus.USER_LOGOUT_FAIL);
        }
        return true;
    }

    /**
     * 账号分页查询
     *
     * @param request 分页请求
     * @return 分页数据
     */
    public BasePage<AdminUserPageDTO> pageAdminUser(AdminUserPageRequest request) {
        AdminUserPageQueryDO adminUserPageQueryDO = adminUserConvertor.convertPageRequestToDO(request);
        IPage<AdminUserPageDO> page = adminUserDao.pageAdminUser(adminUserPageQueryDO);
        return adminUserConvertor.convertPageToDTO(page);
    }

    /**
     * 新增账号
     *
     * @param request 新增请求
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addAdminUser(AdminUserRequest request) {
        // 校验手机号和密码
        AdminUserInfoDO adminUserInfoDO = adminUserDao.queryAdminUserInfoByPhoneNum(request.getPhoneNum());
        if(!Objects.isNull(adminUserInfoDO)) {
            throw new BizException(AdminUserResponseStatus.PHONE_NUM_EXIST);
        }

        // 1.新增角色
        AdminUserInfoDO adminUserDO = adminUserConvertor.convertAddRequestToAdminUserDO(request);
        boolean isAdd = adminUserDao.addAdminUser(adminUserDO);
        if(!isAdd) {
            throw new BizException(CommonResponseConstants.CREATED_FAIL);
        }

        // 2.新增角色权限关联
        for (Long roleId : request.getRoleIds()) {
            AdminUserRoleRelationDO adminUserRoleRelationDO =
                    adminUserRoleRelationConvertor.convertRequestToAdminUserRoleRelationDO(adminUserDO.getId(), roleId);
            adminUserRoleRelationDao.addAdminUserRoleRelation(adminUserRoleRelationDO);
        }
        return true;
    }

    /**
     * 编辑账号
     *
     * @param request 编辑请求
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean editAdminUser(AdminUserRequest request) {
        // 校验手机号和密码
        AdminUserInfoDO adminUserInfoDO =
                adminUserDao.queryEditAdminUserInfoByPhoneNum(request.getPhoneNum(), request.getId());
        if(!Objects.isNull(adminUserInfoDO)) {
            throw new BizException(AdminUserResponseStatus.PHONE_NUM_EXIST);
        }

        // 工人角色不允许在用户管理修改
        List<AdminUserRoleRelationDO> adminUserRoleRelationList =
                adminUserRoleRelationDao.queryRoleByUserId(request.getId());
        List<Long> existAdminRoleIds = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(adminUserRoleRelationList)) {
            existAdminRoleIds = adminUserRoleRelationList.stream()
                    .map(AdminUserRoleRelationDO::getAdminRoleId)
                    .collect(Collectors.toList());
        }

        if(WorkerRoleEnum.judgeWorkerRole(existAdminRoleIds.get(0))) {
            throw new BizException(AdminUserResponseStatus.OTHER_NOT_WORKER);
        }

        // 其他角色也不允许修改成工人角色
        if(WorkerRoleEnum.judgeWorkerRole(request.getRoleIds().get(0))) {
            throw new BizException(AdminUserResponseStatus.OTHER_NOT_WORKER);
        }

        // 1.编辑角色
        AdminUserInfoDO adminUserDO = adminUserConvertor.convertEditRequestToAdminUserDO(request);
        boolean isEdit = adminUserDao.editAdminUser(adminUserDO);
        if(!isEdit) {
            throw new BizException(CommonResponseConstants.UPDATED_FAIL);
        }

        // 2.编辑角色权限关联
        List<Long> adminRoleIds = request.getRoleIds();
        // 新增
        List<Long> finalExistAdminRoleIds = existAdminRoleIds;
        List<Long> addedAdminRoleIds = adminRoleIds.stream()
                .filter(adminRole -> !finalExistAdminRoleIds.contains(adminRole))
                .collect(Collectors.toList());
        for (Long roleId : addedAdminRoleIds) {
            AdminUserRoleRelationDO adminUserRoleRelationDO =
                    adminUserRoleRelationConvertor.convertRequestToAdminUserRoleRelationDO(adminUserDO.getId(), roleId);
            adminUserRoleRelationDao.addAdminUserRoleRelation(adminUserRoleRelationDO);
        }

        // 删除
        List<Long> removedAdminRoleIds = finalExistAdminRoleIds.stream()
                .filter(adminRole -> !adminRoleIds.contains(adminRole))
                .collect(Collectors.toList());
        for (Long roleId : removedAdminRoleIds) {
            AdminUserRoleRelationDO adminUserRoleRelationDO =
                    adminUserRoleRelationConvertor.convertRequestToAdminUserRoleRelationDO(adminUserDO.getId(), roleId);
            adminUserRoleRelationDao.delAdminUserRoleRelation(adminUserRoleRelationDO);
        }

        return true;
    }

    /**
     * 删除账号
     *
     * @param request 删除请求
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAdminUser(AdminUserDeleteRequest request) {
        if (CollectionUtils.isEmpty(request.getIds())) {
            return true;
        }

        for (Long id : request.getIds()) {
            List<AdminUserRoleRelationDO> adminUserRoleRelationDOList = adminUserRoleRelationDao.queryRoleByUserId(id);
            List<Long> existAdminRoleIds = adminUserRoleRelationDOList.stream()
                    .map(AdminUserRoleRelationDO::getAdminRoleId)
                    .collect(Collectors.toList());
            if(WorkerRoleEnum.judgeWorkerRole(existAdminRoleIds.get(0))) {
                throw new BizException(AdminUserResponseStatus.WORKER_FORBID_DELETE);
            }
            AdminUserInfoDO adminUserInfoDO = new AdminUserInfoDO();
            adminUserInfoDO.setId(id);
            adminUserInfoDO.setDelete(Objects.isNull(request.getDelete()) ?
                    DeleteEnum.EXIST.getValue().byteValue() : request.getDelete());

            adminUserDao.editAdminUser(adminUserInfoDO);
        }
        return true;
    }

    public boolean enableAdminUser(AdminUserEnableRequest request) {
        AdminUserInfoDO adminUserInfoDO = new AdminUserInfoDO();
        adminUserInfoDO.setId(request.getId());
        adminUserInfoDO.setStatus(Objects.isNull(request.getEnable()) ?
                StatusEnum.ENABLED.getValue().byteValue() : request.getEnable());

        return adminUserDao.editAdminUser(adminUserInfoDO);
    }

    /**
     * 根据账号查询角色信息
     *
     * @param adminUserId 账号id
     * @return 角色信息
     */
    public List<RoleBaseDTO> showAdminUserDetail(Long adminUserId) {
        List<AdminUserRoleRelationDO> roleRelationList = adminUserRoleRelationDao.queryRoleByUserId(adminUserId);
        if(CollectionUtils.isEmpty(roleRelationList)) {
            return Collections.emptyList();
        }
        List<Long> roleIds = roleRelationList.stream()
                .map(AdminUserRoleRelationDO::getAdminRoleId)
                .collect(Collectors.toList());
        List<RoleDO> roleList = roleDao.queryRoleByIds(roleIds);
        return adminUserRoleRelationConvertor.convertRoleDOToDTO(roleList);
    }

    /**
     * 根据账号查询权限信息
     *
     * @return
     */
    public AdminUserPermissionInfoDTO queryAdminUserPermissionInfo() {
        Long adminUserId = ThreadLocalUtil.getAdminUserId();
        if(Objects.isNull(adminUserId)) {
            return new AdminUserPermissionInfoDTO();
        }
        List<PermissionDO> permissionList;
        if(AdminUserBizConstants.ADMIN_USER.equals(adminUserId)) {
            PermissionDO permissionDO = new PermissionDO();
            permissionDO.setStatus(StatusEnum.ENABLED.getValue().byteValue());
            permissionList = permissionDao.queryPermissionList(permissionDO);
        } else {
            // 查询角色信息
            List<AdminUserRoleRelationDO> adminUserRoleRelationList = adminUserRoleRelationDao.queryRoleByUserId(adminUserId);
            if(CollectionUtils.isEmpty(adminUserRoleRelationList)) {
                return new AdminUserPermissionInfoDTO();
            }
            List<Long> roleIdList = adminUserRoleRelationList.stream().map(AdminUserRoleRelationDO::getAdminRoleId).collect(Collectors.toList());

            // 查询权限信息
            if(CollectionUtils.isEmpty(roleIdList)) {
                return new AdminUserPermissionInfoDTO();
            }
            List<RolePermissionRelationDO> permissionRelationList = rolePermissionRelationDao.queryPermissionByRoleIds(roleIdList);
            if(CollectionUtils.isEmpty(permissionRelationList)) {
                return new AdminUserPermissionInfoDTO();
            }
            List<Long> permissionIdList = permissionRelationList.stream().map(RolePermissionRelationDO::getAdminResourceId).collect(Collectors.toList());

            // 查询权限信息
            if(CollectionUtils.isEmpty(permissionIdList)) {
                return new AdminUserPermissionInfoDTO();
            }
            permissionList = permissionDao.queryPermissionByIds(permissionIdList);
            if(CollectionUtils.isEmpty(permissionList)) {
                return new AdminUserPermissionInfoDTO();
            }
        }

        List<PermissionDTO> permissionDTOList = permissionConvertor.convertPermissionDOToDTO(permissionList);
        return adminUserConvertor.convertToAdminUserPermissionInfoDTO(permissionDTOList);
    }
}
