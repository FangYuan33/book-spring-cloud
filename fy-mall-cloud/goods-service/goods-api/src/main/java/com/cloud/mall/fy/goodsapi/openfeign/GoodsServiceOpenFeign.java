package com.cloud.mall.fy.goodsapi.openfeign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "goods-service", path = "/goods")
public interface GoodsServiceOpenFeign {

}
