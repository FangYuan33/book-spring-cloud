package com.cloud.mall.fy.api.factory;

import com.cloud.mall.fy.api.RemoteOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteOrderFallbackFactory implements FallbackFactory<RemoteOrderService> {

    @Override
    public RemoteOrderService create(Throwable cause) {
        return null;
    }
}
