package com.cloud.mall.fy.recommendservice.controller.param;

import lombok.Data;

@Data
public class IndexConfigQueryParam {

    /**
     * 1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐
     */
    private Integer configType;
}
