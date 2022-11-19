package com.cloud.mall.fy.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockNumDto {

    private Long goodsId;

    private Integer goodsCount;

    public StockNumDto(Long goodsId, Integer goodsCount) {
        this.goodsId = goodsId;
        this.goodsCount = goodsCount;
    }
}
