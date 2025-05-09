package com.worker.infra.dao.impl.role;

import com.worker.common.base.exception.BizException;
import com.worker.common.base.exception.DBExceptionType;
import com.worker.common.constant.ResponseStatus;
import com.worker.common.utils.LoggerUtil;
import com.worker.infra.dao.role.RoleDao;
import com.worker.infra.dataobject.role.RoleDO;
import com.worker.infra.dataobject.role.RolePageDO;
import com.worker.infra.enums.DeleteEnum;
import com.worker.infra.enums.StatusEnum;
import com.worker.infra.mapper.role.RoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色实现层类
 *
 * @author: zhen.gong
 * @date: 2023/11/8 14:29
 */
@Repository
public class RoleDaoImpl implements RoleDao {

    public static final Logger LOGGER = LoggerFactory.getLogger(RoleDaoImpl.class);

    @Resource
    private RoleMapper roleMapper;

    @Override
    public IPage<RolePageDO> pageRole(RoleDO roleDO, Integer offset, Integer limit) {
        Page<RoleDO> page = new Page<>(offset, limit);

        try {
            return roleMapper.selectRolePage(page, roleDO);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), roleDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean queryRoleNameExist(String roleName) {
        LambdaQueryWrapper<RoleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleDO::getDelete, DeleteEnum.EXIST.getValue());
        queryWrapper.eq(StringUtils.isNotEmpty(roleName), RoleDO::getRoleName, roleName);

        try {
            RoleDO roleDO = roleMapper.selectOne(queryWrapper);
            return roleDO != null;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), roleName, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean queryEditRoleNameExist(String roleName, Long id) {
        LambdaQueryWrapper<RoleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleDO::getDelete, DeleteEnum.EXIST.getValue());
        queryWrapper.eq(StringUtils.isNotEmpty(roleName), RoleDO::getRoleName, roleName);
        queryWrapper.ne(RoleDO::getId, id);

        try {
            RoleDO roleDO = roleMapper.selectOne(queryWrapper);
            return roleDO != null;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), roleName, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean addRole(RoleDO roleDO) {
        try {
            int rows = roleMapper.insert(roleDO);
            if (rows <= 0) {
                LoggerUtil.userErrorLog(LOGGER, DBExceptionType.INSERT_EXCEPTION.getMsg(), roleDO);
            }
            return rows > 0;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.INSERT_EXCEPTION.getMsg(), roleDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean updateRole(RoleDO roleDO) {
        try {
            int rows = roleMapper.updateById(roleDO);
            if (rows <= 0) {
                LoggerUtil.userErrorLog(LOGGER, DBExceptionType.UPDATE_EXCEPTION.getMsg(), roleDO);
            }
            return rows > 0;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.UPDATE_EXCEPTION.getMsg(), roleDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<RoleDO> queryRoleList() {
        LambdaQueryWrapper<RoleDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleDO::getDelete, DeleteEnum.EXIST.getValue());
        queryWrapper.eq(RoleDO::getStatus, StatusEnum.ENABLED.getValue());

        try {
            return roleMapper.selectList(queryWrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), null, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<RoleDO> queryRoleByIds(List<Long> roleIds) {
        try {
            return roleMapper.selectBatchIds(roleIds);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), roleIds, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
