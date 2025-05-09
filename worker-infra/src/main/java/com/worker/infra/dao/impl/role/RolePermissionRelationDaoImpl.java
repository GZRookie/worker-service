package com.worker.infra.dao.impl.role;

import com.worker.common.base.exception.BizException;
import com.worker.common.base.exception.DBExceptionType;
import com.worker.common.constant.ResponseStatus;
import com.worker.common.utils.LoggerUtil;
import com.worker.infra.dao.role.RolePermissionRelationDao;
import com.worker.infra.dataobject.role.RolePermissionRelationDO;
import com.worker.infra.mapper.role.RolePermissionRelationMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色权限关联实现层类
 *
 * @author: zhen.gong
 * @date: 2023/11/8 16:01
 */
@Repository
public class RolePermissionRelationDaoImpl implements RolePermissionRelationDao {

    public static final Logger LOGGER = LoggerFactory.getLogger(RolePermissionRelationDaoImpl.class);

    @Resource
    private RolePermissionRelationMapper rolePermissionRelationMapper;

    @Override
    public boolean addRolePermissionRelation(RolePermissionRelationDO rolePermissionRelationDO) {
        try {
            int rows = rolePermissionRelationMapper.insert(rolePermissionRelationDO);
            if (rows <= 0) {
                LoggerUtil.userErrorLog(LOGGER, DBExceptionType.INSERT_EXCEPTION.getMsg(), rolePermissionRelationDO);
            }
            return rows > 0;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.INSERT_EXCEPTION.getMsg(), rolePermissionRelationMapper, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean updateRolePermissionRelation(RolePermissionRelationDO rolePermissionRelationDO) {
        LambdaQueryWrapper<RolePermissionRelationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermissionRelationDO::getAdminRoleId, rolePermissionRelationDO.getAdminRoleId());
        queryWrapper.eq(RolePermissionRelationDO::getAdminResourceId, rolePermissionRelationDO.getAdminResourceId());

        try {
            int rows = rolePermissionRelationMapper.update(rolePermissionRelationDO, queryWrapper);
            if (rows <= 0) {
                LoggerUtil.userErrorLog(LOGGER, DBExceptionType.UPDATE_EXCEPTION.getMsg(), rolePermissionRelationDO);
            }
            return rows > 0;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.UPDATE_EXCEPTION.getMsg(), rolePermissionRelationDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<RolePermissionRelationDO> queryPermissionByRoleId(Long id) {
        LambdaQueryWrapper<RolePermissionRelationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermissionRelationDO::getAdminRoleId, id);

        try {
            return rolePermissionRelationMapper.selectList(queryWrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), id, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<RolePermissionRelationDO> queryPermissionByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<RolePermissionRelationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(RolePermissionRelationDO::getAdminRoleId, roleIds);

        try {
            return rolePermissionRelationMapper.selectList(queryWrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), roleIds, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean delRolePermissionRelation(RolePermissionRelationDO rolePermissionRelationDO) {
        LambdaQueryWrapper<RolePermissionRelationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermissionRelationDO::getAdminRoleId, rolePermissionRelationDO.getAdminRoleId());
        queryWrapper.eq(RolePermissionRelationDO::getAdminResourceId, rolePermissionRelationDO.getAdminResourceId());

        try {
            rolePermissionRelationMapper.delete(queryWrapper);
            return true;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.DELETE_EXCEPTION.getMsg(), rolePermissionRelationDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
