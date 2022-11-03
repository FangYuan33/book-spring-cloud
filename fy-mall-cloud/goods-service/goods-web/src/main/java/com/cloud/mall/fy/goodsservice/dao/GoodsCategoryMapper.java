package com.cloud.mall.fy.goodsservice.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.mall.fy.goodsservice.entity.GoodsCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品分类DAO层
 */
public interface GoodsCategoryMapper extends BaseMapper<GoodsCategory> {

    List<GoodsCategory> selectByLevelAndInParentIds(@Param("categoryLevel") int categoryLevel,
                                                 @Param("parentIds") List<Long> parentIds);
}
