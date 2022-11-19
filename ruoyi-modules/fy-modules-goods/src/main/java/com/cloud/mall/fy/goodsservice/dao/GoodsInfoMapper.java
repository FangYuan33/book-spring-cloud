package com.cloud.mall.fy.goodsservice.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.mall.fy.api.dto.StockNumDto;
import com.cloud.mall.fy.goodsservice.entity.GoodsInfo;

import java.util.List;

/**
 * 商品信息DAO层
 */
public interface GoodsInfoMapper extends BaseMapper<GoodsInfo> {

    /**
     * 批量扣减商品库存
     */
    int updateStockNum(List<StockNumDto> stockNumDtoList);
}
