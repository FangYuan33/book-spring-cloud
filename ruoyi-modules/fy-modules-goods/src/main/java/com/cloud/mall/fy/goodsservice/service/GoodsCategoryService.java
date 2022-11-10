package com.cloud.mall.fy.goodsservice.service;

import com.cloud.mall.fy.goodsservice.controller.param.GoodsCategoryAddParam;
import com.cloud.mall.fy.goodsservice.controller.param.GoodsCategoryEditParam;
import com.cloud.mall.fy.api.dto.FirstLevelCategoryDto;
import com.cloud.mall.fy.goodsservice.entity.GoodsCategory;

import java.util.List;

/**
 * 商品分类服务层
 */
public interface GoodsCategoryService {

    /**
     * 获取首页分类数据
     */
    List<FirstLevelCategoryDto> getCategoriesForIndex();

    /**
     * 新增商品分类
     */
    void save(GoodsCategoryAddParam goodsCategoryAddParam);

    /**
     * 编辑商品分类
     */
    void edit(GoodsCategoryEditParam goodsCategoryEditParam);

    /**
     * 根据ids批量删除
     */
    void deleteByIds(String ids);

    /**
     * 根据ID获取商品分类
     */
    GoodsCategory getById(Long id);
}
