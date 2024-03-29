package com.cloud.mall.fy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品详情页Dto
 */
@Data
public class GoodsDetailDto {

    @ApiModelProperty("商品id")
    private Long id;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品简介")
    private String goodsIntro;

    @ApiModelProperty("商品库存")
    private Integer stockNum;

    @ApiModelProperty("商品价格")
    private Integer sellingPrice;

    @ApiModelProperty("商品标签")
    private String tag;

    @ApiModelProperty("商品原价")
    private Integer originalPrice;

    /**
     * 商品上架状态 1-上架 0-下架
     */
    @ApiModelProperty("商品上架状态")
    private Integer goodsSellStatus;
}
