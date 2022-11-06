package com.cloud.mall.fy.goodsservice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.web.domain.BaseEntityForMall;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("goods_category")
@EqualsAndHashCode(callSuper = true)
public class GoodsCategory extends BaseEntityForMall {

    /**
     * 分类级别(1-一级分类 2-二级分类 3-三级分类)
     */
    private Byte categoryLevel;

    /**
     * 父分类ID
     */
    private Long parentId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 排序值(字段越大越靠前)
     */
    private Integer categoryRank;

    /**
     * 删除标识字段(0-未删除 1-已删除)
     */
    private Byte isDeleted;
}