package com.cloud.mall.fy.recommendservice.entity;

import com.ruoyi.common.datasource.domain.BaseEntityForMall;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 轮播图实体
 *
 * @author FangYuan
 * @since 2022-11-12 11:25:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Carousel extends BaseEntityForMall {

    private String carouselUrl;

    private String redirectUrl;

    private Integer carouselRank;
}