package com.cloud.mall.fy.goodsservice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.datasource.domain.BaseEntityForMall;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品信息实体类
 */
@Data
@TableName("goods_info")
@EqualsAndHashCode(callSuper = true)
public class GoodsInfo extends BaseEntityForMall {

    /**
     * 商品类别ID
     */
    private Long goodsCategoryId;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 商品简介
     */
    private String goodsIntro;

    /**
     * 原价
     */
    private Integer originalPrice;

    /**
     * 现价
     */
    private Integer sellingPrice;

    /**
     * 商品库存
     */
    private Integer stockNum;

    /**
     * 商品标签
     */
    private String tag;

    /**
     * 商品上架状态 1-上架 0-下架
     */
    private Integer goodsSellStatus;

    @TableField(exist = false)
    private Integer isDeleted;
}
