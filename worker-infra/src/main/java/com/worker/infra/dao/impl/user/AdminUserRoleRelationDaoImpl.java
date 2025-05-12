package com.worker.infra.dao.impl.user;

import com.worker.common.base.exception.BizException;
import com.worker.common.base.exception.DBExceptionType;
import com.worker.common.constant.ResponseStatus;
import com.worker.common.utils.LoggerUtil;
import com.worker.infra.dao.user.AdminUserRoleRelationDao;
import com.worker.infra.dataobject.user.AdminUserRoleRelationDO;
import com.worker.infra.mapper.user.AdminUserRoleRelationMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 后台用户关联关系实现层类
 *
 * @author: zhen.gong
 * @date: 2023/11/9 21:14
 */
@Repository
public class AdminUserRoleRelationDaoImpl implements AdminUserRoleRelationDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserRoleRelationDaoImpl.class);

    @Resource
    private AdminUserRoleRelationMapper adminUserRoleRelationMapper;

    @Override
    public boolean addAdminUserRoleRelation(AdminUserRoleRelationDO adminUserRoleRelationDO) {
        try {
            int rows = adminUserRoleRelationMapper.insert(adminUserRoleRelationDO);
            if (rows <= 0) {
                LoggerUtil.userErrorLog(LOGGER, DBExceptionType.INSERT_EXCEPTION.getMsg(), adminUserRoleRelationDO);
            }
            return rows > 0;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.INSERT_EXCEPTION.getMsg(), adminUserRoleRelationDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean delAdminUserRoleRelation(AdminUserRoleRelationDO adminUserRoleRelationDO) {
        LambdaQueryWrapper<AdminUserRoleRelationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(adminUserRoleRelationDO.getAdminUserId()), AdminUserRoleRelationDO::getAdminUserId, adminUserRoleRelationDO.getAdminUserId());
        queryWrapper.eq(Objects.nonNull(adminUserRoleRelationDO.getAdminRoleId()), AdminUserRoleRelationDO::getAdminRoleId, adminUserRoleRelationDO.getAdminRoleId());

        try {
            adminUserRoleRelationMapper.delete(queryWrapper);
            return true;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.DELETE_EXCEPTION.getMsg(), adminUserRoleRelationDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<AdminUserRoleRelationDO> queryRoleByUserId(Long userId) {
        LambdaQueryWrapper<AdminUserRoleRelationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminUserRoleRelationDO::getAdminUserId, userId);

        try {
            return adminUserRoleRelationMapper.selectList(queryWrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), userId, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<AdminUserRoleRelationDO> queryUserIdsByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<AdminUserRoleRelationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CollectionUtils.isNotEmpty(roleIds), AdminUserRoleRelationDO::getAdminRoleId, roleIds);

        try {
            return adminUserRoleRelationMapper.selectList(queryWrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), roleIds, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<AdminUserRoleRelationDO> queryAdminUserRoleRelationsByRoleId(Long roleId) {
        try {
            LambdaQueryWrapper<AdminUserRoleRelationDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AdminUserRoleRelationDO::getAdminRoleId, roleId);
            return adminUserRoleRelationMapper.selectList(queryWrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), roleId, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
