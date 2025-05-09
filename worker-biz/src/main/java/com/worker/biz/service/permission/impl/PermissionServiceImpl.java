package com.worker.biz.service.permission.impl;

import com.worker.biz.constants.CommonResponseConstants;
import com.worker.biz.constants.permission.PermissionRequestEnum;
import com.worker.biz.manager.permission.PermissionManager;
import com.worker.client.api.permission.PermissionService;
import com.worker.client.request.permission.PermissionDeleteRequest;
import com.worker.client.request.permission.PermissionPageRequest;
import com.worker.client.request.permission.PermissionRequest;
import com.worker.client.request.permission.PermissionEnableRequest;
import com.worker.client.response.permisiion.PermissionNodeDTO;
import com.worker.common.base.exception.BizException;
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

import static com.worker.biz.constants.permission.PermissionResponseStatus.*;

/**
 * 权限实现类
 *
 *  @author
 * @date: 2023/11/5 14:36
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Resource
    private PermissionManager permissionManager;

    @Override
    public Result<List<PermissionNodeDTO>> listPermission(PermissionPageRequest request) {
        List<PermissionNodeDTO> list = permissionManager.listPermission(request);
        if(CollectionUtil.isEmpty(list)) {
            return Result.success(Collections.emptyList());
        }

        List<PermissionNodeDTO> permissionNodes = TreeUtils.asTree(list);
        return Result.success(permissionNodes);
    }

    @Override
    public Result<Boolean> addPermission(PermissionRequest request) {
        boolean isSuccess = permissionManager.addPermission(request);
        return isSuccess ? Result.success() : Result.error(CommonResponseConstants.CREATED_FAIL);
    }

    @Override
    public Result<Boolean> editPermission(PermissionRequest request) {
        if(PermissionRequestEnum.EDIT.getBizType().equals(request.getRequestType())
                && Objects.isNull(request.getId())) {
            throw new BizException(PERMISSION_ID_IS_NULL);
        }
        boolean isSuccess = permissionManager.editPermission(request);
        return isSuccess ? Result.success() : Result.error(CommonResponseConstants.CREATED_FAIL);
    }

    @Override
    public Result<Boolean> deletePermission(PermissionDeleteRequest request) {
        if(Objects.isNull(request.getDelete())) {
            throw new BizException(DELETE_PARAM_IS_NULL);
        }
        boolean isSuccess = permissionManager.deletePermission(request);
        return isSuccess ? Result.success() : Result.error(CommonResponseConstants.UPDATED_FAIL);
    }

    @Override
    public Result<Boolean> enablePermission(PermissionEnableRequest request) {
        if(Objects.isNull(request.getEnable())) {
            throw new BizException(ENABLE_PARAM_IS_NULL);
        }
        boolean isSuccess = permissionManager.enablePermission(request);
        return isSuccess ? Result.success() : Result.error(CommonResponseConstants.UPDATED_FAIL);
    }
}
