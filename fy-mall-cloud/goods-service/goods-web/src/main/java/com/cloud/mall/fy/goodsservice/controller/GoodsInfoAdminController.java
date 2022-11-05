package com.cloud.mall.fy.goodsservice.controller;

import com.cloud.mall.fy.common.dto.Result;
import com.cloud.mall.fy.goodsservice.controller.param.GoodsAddParam;
import com.cloud.mall.fy.goodsservice.service.GoodsInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cloud.mall.fy.common.annotation.Log;

import javax.validation.Valid;

@RestController
@RequestMapping("/goods/admin")
@Api(value = "v1", tags = "后台管理系统商品模块接口")
public class GoodsInfoAdminController {

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Log
    @ApiOperation(value = "新增商品信息", notes = "新增商品信息")
    @PostMapping("/add")
    public Result<Object> save(@RequestBody @Valid GoodsAddParam goodsAddParam) {
        goodsInfoService.save(goodsAddParam);
        return Result.success();
    }
}
