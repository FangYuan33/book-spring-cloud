package com.cloud.mall.fy.shoppingcartservice.entity;

import com.ruoyi.common.datasource.domain.BaseEntityForMall;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ShoppingCartItem extends BaseEntityForMall {

    private Long userId;

    private Long goodsId;

    private Integer goodsCount;

}