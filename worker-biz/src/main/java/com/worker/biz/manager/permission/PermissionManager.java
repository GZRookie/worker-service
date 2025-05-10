package com.worker.biz.manager.permission;

import com.worker.biz.convert.permission.PermissionConvertor;
import com.worker.client.response.permisiion.PermissionDTO;
import com.worker.infra.dao.permission.PermissionDao;
import com.worker.infra.dataobject.permission.PermissionDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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
     * @return 权限列表
     */
    public List<PermissionDTO> listPermission() {
        List<PermissionDO> list = permissionDao.queryPermissionList(new PermissionDO());
        return permissionConvertor.convertPermissionDOToDTO(list);
    }
}
