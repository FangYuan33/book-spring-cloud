package com.cloud.mall.fy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单详情页页面订单项Dto
 */
@Data
public class OrderItemDto implements Serializable {

    @ApiModelProperty("商品id")
    private Long goodsId;

    @ApiModelProperty("商品数量")
    private Integer goodsCount;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品图片")
    private String goodsCoverImg;

    @ApiModelProperty("商品价格")
    private Integer sellingPrice;
}
