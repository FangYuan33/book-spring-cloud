package com.cloud.mall.fy.api;

import com.cloud.mall.fy.api.dto.GoodsDetailDto;
import com.cloud.mall.fy.api.dto.StockNumDto;
import com.cloud.mall.fy.api.factory.RemoteGoodsFallbackFactory;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(contextId = "remoteGoodsService", value = ServiceNameConstants.GOODS_SERVICE,
        fallbackFactory = RemoteGoodsFallbackFactory.class)
public interface RemoteGoodsService {

    @GetMapping("/info/inner/detail/{goodsId}")
    R<GoodsDetailDto> getGoodsInfoById(@PathVariable("goodsId") Long goodsId,
                                       @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @GetMapping("/info/inner/detailList")
    R<List<GoodsDetailDto>> getGoodsListById(@RequestParam("goodsIds") List<Long> goodsIds,
                                       @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @PostMapping("/info/inner/reduceCount")
    R<Boolean> reduceCount(@RequestBody List<StockNumDto> stockNumDtoList,
                                             @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
