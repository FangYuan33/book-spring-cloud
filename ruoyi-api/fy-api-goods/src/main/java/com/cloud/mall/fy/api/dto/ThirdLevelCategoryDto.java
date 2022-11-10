package com.cloud.mall.fy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 首页分类数据VO(第三级)
 */
@Data
public class ThirdLevelCategoryDto implements Serializable {

    @ApiModelProperty("当前三级分类id")
    private Long id;

    @ApiModelProperty("当前分类级别")
    private Byte categoryLevel;

    @ApiModelProperty("当前三级分类名称")
    private String categoryName;
}
