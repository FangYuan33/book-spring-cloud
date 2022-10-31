package com.cloud.mall.fy.userservice.postprocessor;

import com.cloud.mall.fy.common.handler.TokenToAdminUserMethodArgumentResolver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

//@Component
public class MethodArgumentResolverProcessor implements BeanPostProcessor {

    @Autowired
    private TokenToAdminUserMethodArgumentResolver tokenToAdminUserMethodArgumentResolver;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("requestMappingHandlerAdapter")) {
            //requestMappingHandlerAdapter进行修改
            RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter) bean;
            List<HandlerMethodArgumentResolver> argumentResolvers = adapter.getArgumentResolvers();

            //添加自定义参数处理器
            argumentResolvers = addArgumentResolvers(argumentResolvers);

            adapter.setArgumentResolvers(argumentResolvers);
        }
        return bean;

    }

    private List<HandlerMethodArgumentResolver> addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();

        // 将自定的添加到最前面
        resolvers.add(tokenToAdminUserMethodArgumentResolver);
        // 将原本的添加后面
        resolvers.addAll(argumentResolvers);
        return resolvers;
    }
}
