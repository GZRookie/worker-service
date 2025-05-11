package com.worker.infra.dao.impl.user;

import com.worker.common.base.exception.BizException;
import com.worker.common.base.exception.DBExceptionType;
import com.worker.common.constant.ResponseStatus;
import com.worker.common.utils.LoggerUtil;
import com.worker.infra.dao.user.AdminUserDao;
import com.worker.infra.dataobject.user.AdminUserInfoDO;
import com.worker.infra.dataobject.user.AdminUserPageDO;
import com.worker.infra.dataobject.user.AdminUserPageQueryDO;
import com.worker.infra.enums.DeleteEnum;
import com.worker.infra.enums.StatusEnum;
import com.worker.infra.mapper.user.AdminUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台用户实现层类
 *
 * @author: zhen.gong
 * @date: 2023/11/3 14:48
 */
@Repository
public class AdminUserDaoImpl implements AdminUserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserDaoImpl.class);

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public AdminUserInfoDO queryAdminUserInfoByPhoneNum(String phoneNum) {
        LambdaQueryWrapper<AdminUserInfoDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(phoneNum), AdminUserInfoDO::getPhoneNum, phoneNum);
        wrapper.eq(AdminUserInfoDO::getDelete, DeleteEnum.EXIST.getValue());

        try {
            return adminUserMapper.selectOne(wrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), phoneNum, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public AdminUserInfoDO queryEditAdminUserInfoByPhoneNum(String phoneNum, Long id) {
        LambdaQueryWrapper<AdminUserInfoDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(phoneNum), AdminUserInfoDO::getPhoneNum, phoneNum);
        wrapper.ne(AdminUserInfoDO::getId, id);

        try {
            return adminUserMapper.selectOne(wrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), phoneNum, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean updateAdminUserInfo(AdminUserInfoDO adminUserInfoDO) {
        LambdaUpdateWrapper<AdminUserInfoDO> updateWrapper = new LambdaUpdateWrapper<>();
        try {
            updateWrapper.eq(AdminUserInfoDO::getPhoneNum, adminUserInfoDO.getPhoneNum());
            int rows = adminUserMapper.update(adminUserInfoDO, updateWrapper);
            if (rows <= 0) {
                LoggerUtil.userErrorLog(LOGGER, DBExceptionType.INSERT_EXCEPTION.getMsg(), adminUserInfoDO);
            }
            return rows > 0;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.INSERT_EXCEPTION.getMsg(), adminUserInfoDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public IPage<AdminUserPageDO> pageAdminUser(AdminUserPageQueryDO adminUserPageQueryDO) {
        IPage<AdminUserInfoDO> page = new Page<>(adminUserPageQueryDO.getOffset(), adminUserPageQueryDO.getLimit());
        try {
            return adminUserMapper.selectAdminUserPage(page, adminUserPageQueryDO);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), adminUserPageQueryDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean addAdminUser(AdminUserInfoDO adminUserDO) {
        try {
            int rows = adminUserMapper.insert(adminUserDO);
            if (rows <= 0) {
                LoggerUtil.userErrorLog(LOGGER, DBExceptionType.INSERT_EXCEPTION.getMsg(), adminUserDO);
            }
            return rows > 0;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.INSERT_EXCEPTION.getMsg(), adminUserDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean editAdminUser(AdminUserInfoDO adminUserDO) {
        try {
            int rows = adminUserMapper.updateById(adminUserDO);
            if (rows <= 0) {
                LoggerUtil.userErrorLog(LOGGER, DBExceptionType.UPDATE_EXCEPTION.getMsg(), adminUserDO);
            }
            return rows > 0;
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.UPDATE_EXCEPTION.getMsg(), adminUserDO, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<AdminUserInfoDO> queryAdminUserInfoByIds(List<Long> adminUserIds) {
        if(CollectionUtils.isEmpty(adminUserIds)) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<AdminUserInfoDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(AdminUserInfoDO::getId, adminUserIds);
        wrapper.eq(AdminUserInfoDO::getDelete, DeleteEnum.EXIST.getValue());

        try {
            return adminUserMapper.selectList(wrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), adminUserIds, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<AdminUserInfoDO> queryAdminUserInfoList() {
        LambdaQueryWrapper<AdminUserInfoDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminUserInfoDO::getDelete, DeleteEnum.EXIST.getValue());

        try {
            return adminUserMapper.selectList(wrapper);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), null, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public AdminUserInfoDO queryAdminUserInfoById(Long sysUserId) {
        try {
            return adminUserMapper.selectById(sysUserId);
        } catch (Exception ex) {
            LoggerUtil.userErrorLog(LOGGER, DBExceptionType.QUERY_EXCEPTION.getMsg(), sysUserId, ex);
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
