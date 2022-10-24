package com.cloud.mall.fy.common.aspect;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.cloud.mall.fy.common.annotation.Log)")
    public void logAspectPointcut() {
    }

    @Around("logAspectPointcut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        ApiOperation apiName = methodSignature.getMethod().getAnnotation(ApiOperation.class);
        PostMapping path = methodSignature.getMethod().getAnnotation(PostMapping.class);

        log.info("{}: {}, 请求参数: {}", apiName.value(), path.value(), JSONArray.toJSONString(joinPoint.getArgs()));

        return joinPoint.proceed();
    }
}