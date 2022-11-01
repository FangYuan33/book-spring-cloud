package com.cloud.mall.fy.goodsservice.service.impl;

import com.cloud.mall.fy.common.enums.CategoryLevelEnum;
import com.cloud.mall.fy.goodsservice.controller.param.IndexCategoryVO;
import com.cloud.mall.fy.goodsservice.controller.param.SecondLevelCategoryVO;
import com.cloud.mall.fy.goodsservice.controller.param.ThirdLevelCategoryVO;
import com.cloud.mall.fy.goodsservice.dao.GoodsCategoryMapper;
import com.cloud.mall.fy.goodsservice.entity.GoodsCategory;
import com.cloud.mall.fy.goodsservice.service.GoodsCategoryService;
import org.graalvm.util.CollectionsUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

    @Resource
    private GoodsCategoryMapper goodsCategoryMapper;


    @Override
    public List<IndexCategoryVO> getCategoriesForIndex() {
        // 获取一级分类
        List<GoodsCategory> firstLeverCategories = goodsCategoryMapper.selectByLevelAndInParentIds(
                CategoryLevelEnum.LEVEL_ONE.getLevel(), Collections.singletonList(0L));
        List<Long> firstIds = firstLeverCategories.stream().map(GoodsCategory::getId).collect(Collectors.toList());
        // 获取二级分类
        List<GoodsCategory> secondLevelCategories = goodsCategoryMapper.selectByLevelAndInParentIds(
                CategoryLevelEnum.LEVEL_TWO.getLevel(), firstIds);
        List<Long> secondIds = secondLevelCategories.stream().map(GoodsCategory::getId).collect(Collectors.toList());
        // 获取三级分类
        List<GoodsCategory> thirdLevelCategories = goodsCategoryMapper.selectByLevelAndInParentIds(
                CategoryLevelEnum.LEVEL_THREE.getLevel(), secondIds);

        Map<Long, List<GoodsCategory>> thirdMap = thirdLevelCategories.stream()
                .collect(Collectors.groupingBy(GoodsCategory::getParentId));
        List<ThirdLevelCategoryVO> thirdLevelCategoryVOS = new ArrayList<>();
        for (Long secondId : thirdMap.keySet()) {
            List<GoodsCategory> thirdCategories = thirdMap.get(secondId);
            for (GoodsCategory thirdCategory : thirdCategories) {
                ThirdLevelCategoryVO temp = new ThirdLevelCategoryVO();
                BeanUtils.copyProperties(thirdCategory, temp);
                thirdLevelCategoryVOS.add(temp);
            }
        }

        List<SecondLevelCategoryVO> secondLevelCategoryVOS = new ArrayList<>();
        // 处理二级分类
        for (GoodsCategory secondLevelCategory : secondLevelCategories) {
            SecondLevelCategoryVO secondLevelCategoryVO = new SecondLevelCategoryVO();
            BeanUtils.copyProperties(secondLevelCategory, secondLevelCategoryVO);
            // 如果该二级分类下有数据则放入 secondLevelCategoryVOS 对象中
            if (thirdMap.containsKey(secondLevelCategory.getId())) {
                // 根据二级分类的id取出thirdLevelCategoryMap分组中的三级分类list
                List<GoodsCategory> tempGoodsCategories = thirdMap.get(secondLevelCategory.getId());
                secondLevelCategoryVO.setThirdLevelCategoryVOS((BeanUtils.copyList(tempGoodsCategories, ThirdLevelCategoryVO.class)));
                secondLevelCategoryVOS.add(secondLevelCategoryVO);
            }
        }
    }
}
