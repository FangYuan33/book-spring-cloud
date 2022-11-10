package com.cloud.mall.fy.api;

import com.cloud.mall.fy.api.dto.GoodsDetailDto;
import com.cloud.mall.fy.api.factory.RemoteGoodsFallbackFactory;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "remoteGoodsService", value = ServiceNameConstants.GOODS_SERVICE,
        fallbackFactory = RemoteGoodsFallbackFactory.class)
public interface RemoteGoodsService {

    @GetMapping("/info/detail/{goodsId}")
    R<GoodsDetailDto> getGoodsInfoById(@PathVariable("goodsId") Long goodsId,
                                       @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
