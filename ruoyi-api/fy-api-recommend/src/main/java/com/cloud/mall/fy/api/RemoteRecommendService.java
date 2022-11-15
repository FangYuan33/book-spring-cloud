package com.cloud.mall.fy.api;

import com.cloud.mall.fy.api.factory.RemoteRecommendFallbackFactory;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(contextId = "remoteRecommendService", value = ServiceNameConstants.RECOMMEND_SERVICE,
        fallbackFactory = RemoteRecommendFallbackFactory.class)
public interface RemoteRecommendService {

}
