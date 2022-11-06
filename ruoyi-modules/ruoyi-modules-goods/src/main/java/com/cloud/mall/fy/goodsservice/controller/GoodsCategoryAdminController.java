package com.cloud.mall.fy.goodsservice.controller;

import com.cloud.mall.fy.goodsservice.controller.param.GoodsCategoryAddParam;
import com.cloud.mall.fy.goodsservice.controller.param.GoodsCategoryEditParam;
import com.cloud.mall.fy.goodsservice.service.GoodsCategoryService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(value = "v1", tags = "后台管理系统分类模块接口")
@RequestMapping("/category/admin")
public class GoodsCategoryAdminController {

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @ApiOperation(value = "新增分类", notes = "新增分类")
    @PostMapping("/add")
    public AjaxResult save(@RequestBody @Valid GoodsCategoryAddParam goodsCategoryAddParam) {
        goodsCategoryService.save(goodsCategoryAddParam);
        return AjaxResult.success();
    }

    @ApiOperation(value = "获取单条分类信息", notes = "根据id查询")
    @GetMapping("/detail/{id}")
    public AjaxResult info(@PathVariable("id") Long id) {
        return AjaxResult.success(goodsCategoryService.getById(id));
    }

    @ApiOperation(value = "修改分类信息", notes = "修改分类信息")
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody @Valid GoodsCategoryEditParam goodsCategoryEditParam) {
        goodsCategoryService.edit(goodsCategoryEditParam);
        return AjaxResult.success();
    }

    @ApiOperation(value = "批量删除分类信息", notes = "批量删除分类信息")
    @PostMapping(value = "/batchDelete")
    public AjaxResult batchDelete(@RequestBody String ids) {
        goodsCategoryService.deleteByIds(ids);
        return AjaxResult.success();
    }
}
