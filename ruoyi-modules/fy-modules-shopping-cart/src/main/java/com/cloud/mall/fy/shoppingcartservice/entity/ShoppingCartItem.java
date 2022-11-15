package com.cloud.mall.fy.shoppingcartservice.entity;

import com.ruoyi.common.datasource.domain.BaseEntityForMall;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShoppingCartItem extends BaseEntityForMall {

    private Long userId;

    private Long goodsId;

    private Integer goodsCount;

}