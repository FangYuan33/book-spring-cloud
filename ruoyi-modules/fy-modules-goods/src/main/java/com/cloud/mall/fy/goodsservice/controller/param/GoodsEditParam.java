package com.cloud.mall.fy.goodsservice.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class GoodsEditParam {

    @ApiModelProperty("待修改商品id")
    @NotNull(message = "商品id不能为空")
    @Min(value = 1, message = "商品id不能为空")
    private Long goodsId;

    @ApiModelProperty("商品名称")
    @Length(max = 128, message = "商品名称内容过长")
    private String goodsName;

    @ApiModelProperty("商品简介")
    @Length(max = 200, message = "商品简介内容过长")
    private String goodsIntro;

    @ApiModelProperty("分类id")
    @Min(value = 1, message = "分类id最低为1")
    private Long goodsCategoryId;

    @ApiModelProperty("商品主图")
    @NotEmpty(message = "商品主图不能为空")
    private String goodsCoverImg;

    @ApiModelProperty("originalPrice")
    @Min(value = 1, message = "originalPrice最低为1")
    @Max(value = 1000000, message = "originalPrice最高为1000000")
    private Integer originalPrice;

    @ApiModelProperty("sellingPrice")
    @Min(value = 1, message = "sellingPrice最低为1")
    @Max(value = 1000000, message = "sellingPrice最高为1000000")
    private Integer sellingPrice;

    @ApiModelProperty("库存")
    @Min(value = 1, message = "库存最低为1")
    @Max(value = 100000, message = "库存最高为100000")
    private Integer stockNum;

    @ApiModelProperty("商品标签")
    @Length(max = 16, message = "商品标签内容过长")
    private String tag;

    private Integer goodsSellStatus;

    @ApiModelProperty("商品详情")
    private String goodsDetailContent;
}