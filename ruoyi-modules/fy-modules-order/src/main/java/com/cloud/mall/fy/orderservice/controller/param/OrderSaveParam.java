package com.cloud.mall.fy.orderservice.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 保存订单param
 */
@Data
public class OrderSaveParam implements Serializable {

    @ApiModelProperty("购物车ids")
    private List<Long> cartItemIds;

    @ApiModelProperty("地址id")
    private Long addressId;
}
