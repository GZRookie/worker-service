package com.worker.biz.service.role.impl;

import com.worker.biz.constants.CommonResponseConstants;
import com.worker.biz.constants.role.RoleRequestEnum;
import com.worker.biz.constants.role.RoleResponseStatus;
import com.worker.biz.manager.role.RoleManager;
import com.worker.client.api.role.RoleService;
import com.worker.client.request.role.RoleDeleteRequest;
import com.worker.client.request.role.RoleEnableRequest;
import com.worker.client.request.role.RolePageRequest;
import com.worker.client.request.role.RoleRequest;
import com.worker.client.response.permisiion.PermissionBaseNodeDTO;
import com.worker.client.response.role.RoleDTO;
import com.worker.common.base.exception.BizException;
import com.worker.common.base.object.BasePage;
import com.worker.common.base.object.Result;
import com.worker.common.utils.TreeUtils;
import cn.hutool.core.collection.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.worker.biz.constants.role.RoleResponseStatus.ROLE_ID_IS_NULL;

/**
 * 角色实现类
 *
 *  @author
 * @date: 2023/11/7 23:08
 */
@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Resource
    private RoleManager roleManager;

    @Override
    public Result<BasePage<RoleDTO>> pageRole(RolePageRequest request) {
        BasePage<RoleDTO> page = roleManager.pageRole(request);
        if (Objects.isNull(page.getData())) {
            return Result.success(BasePage.empty());
        }
        return Result.success(page);
    }

    @Override
    public Result<Boolean> addRole(RoleRequest request) {
        boolean isAdd = roleManager.addRole(request);
        return isAdd ? Result.success() : Result.error(CommonResponseConstants.CREATED_FAIL);
    }

    @Override
    public Result<List<PermissionBaseNodeDTO>> showRoleDetail(Long roleId) {
        List<PermissionBaseNodeDTO> list = roleManager.showRoleDetail(roleId);
        if(CollectionUtil.isEmpty(list)) {
            return Result.success(Collections.emptyList());
        }

        List<PermissionBaseNodeDTO> permissionNodes = TreeUtils.asTree(list);
        return Result.success(permissionNodes);
    }

    @Override
    public Result<Boolean> editRole(RoleRequest request) {
        if(RoleRequestEnum.EDIT.getBizType().equals(request.getRequestType())
                && Objects.isNull(request.getId())) {
            throw new BizException(ROLE_ID_IS_NULL);
        }
        boolean isEdit = roleManager.editRole(request);
        return isEdit ? Result.success() : Result.error(CommonResponseConstants.UPDATED_FAIL);
    }

    @Override
    public Result<Boolean> deleteRole(RoleDeleteRequest request) {
        if(Objects.isNull(request.getDelete())) {
            throw new BizException(RoleResponseStatus.DELETE_PARAM_IS_NULL);
        }
        boolean isSuccess = roleManager.deleteRole(request);
        return isSuccess ? Result.success() : Result.error(CommonResponseConstants.UPDATED_FAIL);
    }

    @Override
    public Result<Boolean> enableRole(RoleEnableRequest request) {
        if(Objects.isNull(request.getEnable())) {
            throw new BizException(RoleResponseStatus.ENABLE_PARAM_IS_NULL);
        }
        boolean isSuccess = roleManager.enableRole(request);
        return isSuccess ? Result.success() : Result.error(CommonResponseConstants.UPDATED_FAIL);
    }

    @Override
    public Result<List<RoleDTO>> list() {
        return Result.success(roleManager.list());
    }
}
