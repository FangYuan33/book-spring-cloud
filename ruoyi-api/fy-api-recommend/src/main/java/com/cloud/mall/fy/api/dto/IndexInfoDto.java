package com.cloud.mall.fy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class IndexInfoDto implements Serializable {

    @ApiModelProperty("轮播图(列表)")
    private List<CarouselDto> carousels;

    @ApiModelProperty("首页热销商品(列表)")
    private List<IndexConfigGoodsDto> hotGoods;

    @ApiModelProperty("首页新品推荐(列表)")
    private List<IndexConfigGoodsDto> newGoods;

    @ApiModelProperty("首页推荐商品(列表)")
    private List<IndexConfigGoodsDto> recommendGoods;
}
