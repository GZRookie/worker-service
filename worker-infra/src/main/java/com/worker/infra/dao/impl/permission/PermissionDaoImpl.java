package com.worker.infra.dao.impl.permission;

import com.worker.common.base.exception.BizException;
import com.worker.common.base.exception.DBExceptionType;
import com.worker.common.constant.ResponseStatus;
import com.worker.common.utils.LoggerUtil;
import com.worker.infra.dao.permission.PermissionDao;
import com.worker.infra.dataobject.permission.PermissionDO;
import com.worker.infra.enums.DeleteEnum;
import com.worker.infra.mapper.permission.PermissionMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 权限实现层类
 *
 * @author: zhen.gong
 * @date: 2023/11/6 15:44
 */
@Repository
public class PermissionDaoImpl implements PermissionDao {

    public static final Logger LOGGER = LoggerFactory.getLogger(PermissionDaoImpl.class);

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public List<PermissionDO> queryPermissionList(PermissionDO permissionDO) {
        LambdaQueryWrapper<PermissionDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(permissionDO.getName()) , PermissionDO::getName, permissionDO.getName());
        queryWrapper.eq(Objects.nonNull(permissionDO.getStatus()), PermissionDO::getStatus, permissionDO.getStatus());
        queryWrapper.eq(PermissionDO::getDelete, DeleteEnum.EXIST.getValue());
        queryWrapper.orderByAsc(PermissionDO::getSort);

        try {
            return permissionMapper.selectList(queryWrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), permissionDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<PermissionDO> queryPermissionByIds(List<Long> permissionIds) {
        try {
            return permissionMapper.selectBatchIds(permissionIds);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), permissionIds, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
