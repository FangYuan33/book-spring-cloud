package com.cloud.mall.fy.goodsservice.controller.param;

import lombok.Data;

/**
 * 商品列表查询参数
 */
@Data
public class GoodsListParam {

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 商品上架状态
     */
    private Integer goodsSellStatus;
}
