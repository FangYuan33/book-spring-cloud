package com.cloud.mall.fy.goodsservice.controller;

import com.cloud.mall.fy.common.dto.Result;
import com.cloud.mall.fy.goodsservice.controller.param.GoodsCategoryAddParam;
import com.cloud.mall.fy.goodsservice.service.GoodsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(value = "v1", tags = "后台管理系统分类模块接口")
@RequestMapping("/category/admin")
public class GoodsCategoryAdminController {

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @ApiOperation(value = "新增分类", notes = "新增分类")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Object> save(@RequestBody @Valid GoodsCategoryAddParam goodsCategoryAddParam) {
        goodsCategoryService.save(goodsCategoryAddParam);
        return Result.success();
    }
}
