package com.cloud.mall.fy.recommendservice.entity;

import com.ruoyi.common.datasource.domain.BaseEntityForMall;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class IndexConfig extends BaseEntityForMall {

    private String configName;

    /**
     * 1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐
     */
    private Integer configType;

    private Long goodsId;

    private String redirectUrl;

    private Integer configRank;

}