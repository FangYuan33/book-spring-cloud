package com.cloud.mall.fy.common.handler;

import com.cloud.mall.fy.common.annotation.TokenToAdminUser;
import com.cloud.mall.fy.common.cache.CacheService;
import com.cloud.mall.fy.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Admin user token 验证resolver
 */
@Component
public class TokenToAdminUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private CacheService cacheService;

    /**
     * 标注了token验证注解返回true，执行resolveArgument方法进行token验证
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(TokenToAdminUser.class);
    }

    @SuppressWarnings("all")
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        TokenToAdminUser adminUserToken = parameter.getParameterAnnotation(TokenToAdminUser.class);
        String token = webRequest.getHeader(adminUserToken.value());

        if (StringUtils.hasText(token)) {
            Object value = cacheService.getByKey(token);

            if (value == null) {
                throw new BusinessException("ADMIN_NOT_LOGIN_ERROR");
            } else {
                return value;
            }
        } else {
            throw new BusinessException("ADMIN_NOT_LOGIN_ERROR");
        }
    }
}
