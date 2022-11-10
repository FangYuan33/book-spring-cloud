package com.cloud.mall.fy.goodsservice.service;

import com.cloud.mall.fy.goodsservice.controller.param.GoodsAddParam;
import com.cloud.mall.fy.api.dto.GoodsDetailDto;

/**
 * 商品信息服务层
 */
public interface GoodsInfoService {

    /**
     * 根据ID 获取商品详情
     */
    GoodsDetailDto getById(Long goodsId);

    /**
     * 保存商品信息
     */
    void save(GoodsAddParam goodsAddParam);
}
