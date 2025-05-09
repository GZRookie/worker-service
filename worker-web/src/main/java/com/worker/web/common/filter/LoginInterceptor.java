package com.worker.web.common.filter;

import com.worker.biz.common.properties.JwtProperties;
import com.worker.biz.common.utils.JwtTokenUtil;
import com.worker.biz.constants.jwt.JwtBizConstants;
import com.worker.biz.constants.user.AdminUserResponseStatus;
import com.worker.client.response.jwt.UserHeaderResponse;
import com.worker.common.base.exception.BizException;
import com.worker.common.constant.ResponseStatus;
import com.worker.common.utils.IpAddressUtils;
import com.worker.common.utils.LoggerUtil;
import com.worker.common.utils.ThreadLocalUtil;
import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.jwt.SaJwtUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.jwt.JWT;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 鉴权处理拦截器
 *
 *  @author
 * @date: 2023/11/2 21:22
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);
    private static final String HEALTH_CHECK = "/health/check";

    @Resource
    private SaTokenConfig saTokenConfig;
    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 从Request中获取参数
        String requestUrl = String.valueOf(request.getRequestURI());
        String ip = IpAddressUtils.getIp(request);

        // 追加请求日志
        if (!requestUrl.contains(HEALTH_CHECK)) {
            LoggerUtil.userInfoLog(LOGGER, String.format("拦截器预处理，请求IP: %s，请求URI：%s", ip, requestUrl), null);
        }

        // 检查token
        if (!isPass(requestUrl)) {
            checkToken(request);
        }

        // 添加IP信息
        if (StringUtils.isNotBlank(ip)) {
            ThreadLocalUtil.setAddressIp(ip);
        }

        return true;
    }

    /**
     * 是否无需登录直接通过的接口
     *
     * @param url 资源名
     * @return True-通过 False-需Token鉴权
     */
    private boolean isPass(String url) {
        List<String> list = jwtProperties.getPermitMethods();
        LoggerUtil.userInfoLog(LOGGER, list.toString(), list);
        return list.contains(url);
    }

    /**
     * 校验token
     *
     * @param request 请求
     */
    private void checkToken(HttpServletRequest request) {
        // 获取JWT令牌并校验
        String jwtToken = request.getHeader(saTokenConfig.getTokenName());
        if (ObjectUtils.isEmpty(jwtToken)) {
            LoggerUtil.userInfoLog(LOGGER, jwtToken, jwtToken);
            throw new BizException(ResponseStatus.UNAUTHORIZED);
        }

        JWT jwt;
        try {
            jwt = SaJwtUtil.parseToken(jwtToken,
                    jwtProperties.getLoginType(), jwtProperties.getSecret(), false);
        } catch (Exception ex) {
            LoggerUtil.userInfoLog(LOGGER, ex.getMessage(), ex);
            throw new BizException(ResponseStatus.UNAUTHORIZED);
        }

        // 解析用户信息
        UserHeaderResponse userHeader = jwtTokenUtil.getUserHeaderFromJWT(jwt);
        if (ObjectUtils.isEmpty(userHeader) || (StringUtils.isEmpty(userHeader.getAdminUserId())
                && StringUtils.isEmpty(userHeader.getFrontUserId()))) {
            throw new BizException(ResponseStatus.UNAUTHORIZED);
        }
        LoggerUtil.userInfoLog(LOGGER, "userHeader :", userHeader);

        String identity = (String) StpUtil.getExtra(JwtBizConstants.IDENTITY);
        if(JwtBizConstants.ADMIN.equals(identity)) {// 后台
            ThreadLocalUtil.setAdminUserId(Long.valueOf(userHeader.getAdminUserId()));
        }

        // 校验token
        checkLoginStatus();
    }

    /**
     * 检查登录状态
     */
    private void checkLoginStatus() throws BizException {
        String token = SaHolder.getRequest().getHeader(saTokenConfig.getTokenName());
        if (token == null) {
            throw new BizException(AdminUserResponseStatus.USER_NOT_LOGIN);
        }
        if (!StpUtil.isLogin()) {
            throw new BizException(ResponseStatus.UNAUTHORIZED);
        }
    }
}
