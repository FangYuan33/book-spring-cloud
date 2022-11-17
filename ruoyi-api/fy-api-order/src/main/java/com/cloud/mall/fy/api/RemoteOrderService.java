package com.cloud.mall.fy.api;

import com.cloud.mall.fy.api.factory.RemoteOrderFallbackFactory;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(contextId = "remoteOrderService", value = ServiceNameConstants.ORDER_SERVICE,
        fallbackFactory = RemoteOrderFallbackFactory.class)
public interface RemoteOrderService {

}
