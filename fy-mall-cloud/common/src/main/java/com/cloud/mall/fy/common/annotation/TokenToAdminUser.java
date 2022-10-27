package com.cloud.mall.fy.common.annotation;

import java.lang.annotation.*;

/**
 * admin user 验证token的注解
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenToAdminUser {

    /**
     * 在请求头中的key值
     */
    String value() default "adminUser";
}
