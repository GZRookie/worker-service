package com.worker.biz.common.utils;

import com.worker.biz.common.properties.JwtProperties;
import com.worker.client.response.jwt.UserHeaderResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import org.springframework.stereotype.Component;

/**
 * JWT Token工具类
 *
 *  @author
 * @date: 2023/11/3 22:45
 */
@Component
public class JwtTokenUtil {

    private final JwtProperties jwtProperties;

    public JwtTokenUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * 从JWT中解析获取用户名
     *
     * @param jwt
     * @return 用户名
     */
    public String getUserIdFromJWT(JWT jwt) {
        String adminUserId;
        try {
            JSONObject payloads = jwt.getPayloads();
            adminUserId = payloads.getStr(jwtProperties.getAdminUserIdKey());
        } catch (Exception e) {
            adminUserId = null;
        }
        return adminUserId;
    }


    /**
     * 从令牌中获取用户请求头数据
     *
     * @param jwt jwt对象
     * @return 用户名
     */
    public UserHeaderResponse getUserHeaderFromJWT(JWT jwt) {
        UserHeaderResponse userHeader;
        try {
            JSONObject payloads = jwt.getPayloads();
            userHeader = UserHeaderResponse.builder()
                    .adminUserId(payloads.getStr(jwtProperties.getAdminUserIdKey()))
                    .frontUserId(payloads.getStr(jwtProperties.getFrontUserIdKey()))
                    .eff(payloads.getStr(JwtProperties.EFF))
                    .phoneNum(payloads.getStr(jwtProperties.getPhoneNumKey())).build();
        } catch (Exception e) {
            userHeader = null;
        }
        return userHeader;
    }
}
