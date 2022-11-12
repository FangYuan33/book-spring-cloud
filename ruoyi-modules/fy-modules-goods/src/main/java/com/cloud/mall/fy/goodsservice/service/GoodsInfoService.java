package com.cloud.mall.fy.goodsservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.mall.fy.goodsservice.controller.param.GoodsAddParam;
import com.cloud.mall.fy.api.dto.GoodsDetailDto;
import com.cloud.mall.fy.goodsservice.controller.param.GoodsEditParam;
import com.cloud.mall.fy.goodsservice.controller.param.GoodsListParam;
import com.cloud.mall.fy.goodsservice.entity.GoodsInfo;

import java.util.List;

/**
 * 商品信息服务层
 */
public interface GoodsInfoService extends IService<GoodsInfo> {

    /**
     * 根据ID 获取商品详情
     */
    GoodsDetailDto getById(Long goodsId);

    /**
     * 保存商品信息
     */
    void save(GoodsAddParam goodsAddParam);

    /**
     * 列表展示
     */
    List<GoodsDetailDto> list(GoodsListParam listParam);

    /**
     * 根据ID编辑
     */
    void editById(GoodsEditParam goodsEditParam);
}
