package com.worker.web.common.anno;

import java.lang.annotation.*;

/**
 * 操作鉴权Check
 *
 *  @author
 * @date: 2024-01-10 12:32
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckOperateAuth {

    /**
     * 权限码
     *
     * @return 权限码 例: team:add
     */
    String code();

    /**
     * 是否需要检验，前台用户没有登录需要获取数据，比如隐私协议
     *
     * @return true：校验 false：不需要检验
     */
    boolean isPass() default false;

    /**
     * 权限码描述
     *
     * @return 权限描述 例：权限新增
     */
    String desc() default "";

}
