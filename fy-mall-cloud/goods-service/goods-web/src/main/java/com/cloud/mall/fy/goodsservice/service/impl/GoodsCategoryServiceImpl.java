package com.cloud.mall.fy.goodsservice.service.impl;

import com.cloud.mall.fy.common.enums.CategoryLevelEnum;
import com.cloud.mall.fy.common.utils.BeanUtils;
import com.cloud.mall.fy.goodsservice.controller.vo.FirstLevelCategoryVO;
import com.cloud.mall.fy.goodsservice.controller.vo.SecondLevelCategoryVO;
import com.cloud.mall.fy.goodsservice.controller.vo.ThirdLevelCategoryVO;
import com.cloud.mall.fy.goodsservice.dao.GoodsCategoryMapper;
import com.cloud.mall.fy.goodsservice.entity.GoodsCategory;
import com.cloud.mall.fy.goodsservice.service.GoodsCategoryService;
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
    public List<FirstLevelCategoryVO> getCategoriesForIndex() {
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

        // 二级分类视图
        List<SecondLevelCategoryVO> secondLevelCategoryVOList =
                getSecondLevelCategoryVOWithThirdLevelCategory(thirdLevelCategories, secondLevelCategories);

        // 处理并返回一级分类视图
        return getIndexCategoryVOWithSecondLevelCategory(secondLevelCategoryVOList, firstLeverCategories);
    }

    /**
     * 将对应的三级分类挂到所属的二级分类并返回二级分类视图
     */
    private List<SecondLevelCategoryVO> getSecondLevelCategoryVOWithThirdLevelCategory(
                                                                List<GoodsCategory> thirdLevelCategories,
                                                                List<GoodsCategory> secondLevelCategories) {
        // 三级分类根据二级父类分类
        Map<Long, List<GoodsCategory>> thirdMap = thirdLevelCategories.stream()
                .collect(Collectors.groupingBy(GoodsCategory::getParentId));

        // 处理二级分类，将二级分类下挂对应的三级分类
        List<SecondLevelCategoryVO> secondLevelCategoryVOS = new ArrayList<>();
        for (GoodsCategory secondLevelCategory : secondLevelCategories) {
            SecondLevelCategoryVO secondLevelCategoryVO =
                    BeanUtils.copyProperties(secondLevelCategory, SecondLevelCategoryVO.class);

            // 如果该二级分类下有三级分类数据则处理
            if (thirdMap.containsKey(secondLevelCategory.getId())) {
                // 根据二级分类的id取出thirdLevelCategoryMap分组中的三级分类list
                List<GoodsCategory> tempGoodsCategories = thirdMap.get(secondLevelCategory.getId());
                secondLevelCategoryVO.setThirdLevelCategoryVOS(
                        BeanUtils.copyList(tempGoodsCategories, ThirdLevelCategoryVO.class));
            }
            secondLevelCategoryVOS.add(secondLevelCategoryVO);
        }

        return secondLevelCategoryVOS;
    }

    private ArrayList<FirstLevelCategoryVO> getIndexCategoryVOWithSecondLevelCategory(
                                                                List<SecondLevelCategoryVO> secondLevelCategoryVOList,
                                                                List<GoodsCategory> firstLeverCategories) {
        // 处理一级分类，对应挂靠它的所属二级分类
        ArrayList<FirstLevelCategoryVO> firstLevelCategoryVOS = new ArrayList<>();
        Map<Long, List<SecondLevelCategoryVO>> secondMap = secondLevelCategoryVOList.stream()
                .collect(Collectors.groupingBy(SecondLevelCategoryVO::getParentId));
        for (GoodsCategory firstLeverCategory : firstLeverCategories) {
            FirstLevelCategoryVO firstLevelCategoryVO = BeanUtils.copyProperties(firstLeverCategory, FirstLevelCategoryVO.class);

            if (secondMap.containsKey(firstLeverCategory.getId())) {
                List<SecondLevelCategoryVO> tempSecondCategories = secondMap.get(firstLeverCategory.getId());
                firstLevelCategoryVO.setSecondLevelCategoryVOS(tempSecondCategories);
            }
            firstLevelCategoryVOS.add(firstLevelCategoryVO);
        }

        return firstLevelCategoryVOS;
    }
}
