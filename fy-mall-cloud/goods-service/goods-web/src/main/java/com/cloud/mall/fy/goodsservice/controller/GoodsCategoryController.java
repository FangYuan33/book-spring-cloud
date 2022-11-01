package com.cloud.mall.fy.goodsservice.controller;

import com.cloud.mall.fy.common.dto.Result;
import com.cloud.mall.fy.common.exception.BusinessException;
import com.cloud.mall.fy.goodsservice.controller.param.IndexCategoryVO;
import com.cloud.mall.fy.goodsservice.service.GoodsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/goods/category")
@Api(value = "v1", tags = "分类页面接口")
public class GoodsCategoryController {

    @Resource
    private GoodsCategoryService goodsCategoryService;

    @GetMapping("/listAll")
    @ApiOperation(value = "获取分类数据", notes = "分类页面使用")
    public Result<List<IndexCategoryVO>> getCategories() {
        List<IndexCategoryVO> categories = goodsCategoryService.getCategoriesForIndex();

        if (CollectionUtils.isEmpty(categories)) {
            throw new BusinessException("未获取到分类数据");
        }

        return Result.success(categories);
    }
}
