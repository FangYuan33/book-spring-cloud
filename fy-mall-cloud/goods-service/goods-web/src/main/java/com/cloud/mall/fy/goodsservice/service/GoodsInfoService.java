package com.cloud.mall.fy.goodsservice.service;

import com.cloud.mall.fy.goodsservice.controller.param.GoodsDetailVO;

/**
 * 商品信息服务层
 */
public interface GoodsInfoService {

    /**
     * 根据ID 获取商品详情
     */
    GoodsDetailVO getById(Long goodsId);
}
