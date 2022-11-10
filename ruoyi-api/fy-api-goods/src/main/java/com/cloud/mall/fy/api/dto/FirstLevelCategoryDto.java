package com.cloud.mall.fy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 首页分类数据VO
 */
@Data
public class FirstLevelCategoryDto {

    @ApiModelProperty("当前一级分类id")
    private Long id;

    @ApiModelProperty("当前分类级别")
    private Byte categoryLevel;

    @ApiModelProperty("当前一级分类名称")
    private String categoryName;

    @ApiModelProperty("二级分类列表")
    private List<SecondLevelCategoryDto> secondLevelCategoryDtos;
}
