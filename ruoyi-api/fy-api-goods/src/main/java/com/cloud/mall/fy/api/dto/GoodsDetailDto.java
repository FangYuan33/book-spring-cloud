package com.cloud.mall.fy.api.dto;

import lombok.Data;

/**
 * 商品详情页VO
 */
@Data
public class GoodsDetailDto {

    private Long id;

    private String goodsName;

    private String goodsIntro;

    private String goodsCoverImg;

    private Integer sellingPrice;

    private String tag;

    private String[] goodsCarouselList;

    private Integer originalPrice;

    private String goodsDetailContent;
}
