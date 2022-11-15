package com.cloud.mall.fy.api.factory;

import com.cloud.mall.fy.api.RemoteShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteShoppingCartFallbackFactory implements FallbackFactory<RemoteShoppingCartService> {

    @Override
    public RemoteShoppingCartService create(Throwable cause) {
        return null;
    }
}
