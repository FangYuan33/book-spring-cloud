package com.cloud.mall.fy.userservice.config;

import com.cloud.mall.fy.common.dto.Result;
import com.cloud.mall.fy.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        if (e instanceof BusinessException) {
            return Result.fail(e.getMessage());
        } else {
            log.error(e.getMessage(), e);
            return Result.fail("系统异常");
        }
    }
}
