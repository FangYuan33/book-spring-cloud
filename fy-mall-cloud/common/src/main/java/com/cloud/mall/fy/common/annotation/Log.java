package com.cloud.mall.fy.common.annotation;

import java.lang.annotation.*;

/**
 * 接口请求打印日志
 *
 * @author FangYuan
 * @since 2022-10-24 16:48:59
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

}
