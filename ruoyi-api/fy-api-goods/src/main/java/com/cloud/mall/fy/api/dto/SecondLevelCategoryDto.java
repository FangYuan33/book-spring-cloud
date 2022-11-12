package com.cloud.mall.fy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 首页分类数据VO(第二级)
 */
@Data
public class SecondLevelCategoryDto implements Serializable {

    @ApiModelProperty("当前二级分类id")
    private Long id;

    @ApiModelProperty("父级分类id")
    private Long parentId;

    @ApiModelProperty("当前分类级别")
    private Integer categoryLevel;

    @ApiModelProperty("当前二级分类名称")
    private String categoryName;

    @ApiModelProperty("三级分类列表")
    private List<ThirdLevelCategoryDto> thirdLevelCategoryDtos;
}
