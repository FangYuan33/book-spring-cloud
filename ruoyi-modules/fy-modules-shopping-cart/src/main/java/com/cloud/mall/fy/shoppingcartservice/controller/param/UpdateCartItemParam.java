package com.cloud.mall.fy.shoppingcartservice.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 添加购物项param
 */
@Data
public class UpdateCartItemParam implements Serializable {

    @ApiModelProperty("商品数量")
    private Integer goodsCount;

    @ApiModelProperty("购物项id")
    private Long shoppingCartItemId;
}
