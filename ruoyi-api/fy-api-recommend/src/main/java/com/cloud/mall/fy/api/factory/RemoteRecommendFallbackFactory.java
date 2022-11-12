package com.cloud.mall.fy.api.factory;

import com.cloud.mall.fy.api.RemoteRecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteRecommendFallbackFactory implements FallbackFactory<RemoteRecommendService> {
    @Override
    public RemoteRecommendService create(Throwable cause) {
        log.error("推荐模块服务调用失败: {}", cause.getMessage());

        return null;
    }
}
