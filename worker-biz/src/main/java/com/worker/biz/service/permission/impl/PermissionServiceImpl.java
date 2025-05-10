package com.worker.biz.service.permission.impl;

import com.worker.biz.manager.permission.PermissionManager;
import com.worker.client.api.permission.PermissionService;
import com.worker.client.response.permisiion.PermissionDTO;
import com.worker.common.base.object.Result;
import cn.hutool.core.collection.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

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
    public Result<List<PermissionDTO>> listPermission() {
        List<PermissionDTO> list = permissionManager.listPermission();
        if(CollectionUtil.isEmpty(list)) {
            return Result.success(Collections.emptyList());
        }

        return Result.success(list);
    }
}
