package com.cloud.mall.fy.api;

import com.cloud.mall.fy.api.factory.RemoteShoppingCartFallbackFactory;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(contextId = "remoteShoppingCartService", value = ServiceNameConstants.SHOPPING_CART_SERVICE,
        fallbackFactory = RemoteShoppingCartFallbackFactory.class)
public interface RemoteShoppingCartService {

}
