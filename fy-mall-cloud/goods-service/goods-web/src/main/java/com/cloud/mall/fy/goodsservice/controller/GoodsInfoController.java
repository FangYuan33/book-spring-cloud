package com.cloud.mall.fy.goodsservice.controller;

import com.cloud.mall.fy.common.dto.Result;
import com.cloud.mall.fy.goodsservice.controller.param.GoodsDetailVO;
import com.cloud.mall.fy.goodsservice.service.GoodsInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goods/info")
@Api(value = "v1", tags = "商品信息页面接口")
public class GoodsInfoController {

    @Autowired
    private GoodsInfoService goodsInfoService;

    @PostMapping("/detail/{goodsId}")
    @ApiOperation(value = "商品详情接口", notes = "传参为商品id")
    public Result<GoodsDetailVO> goodsDetail(@ApiParam(value = "商品id") @PathVariable("goodsId") Long goodsId) {
        return Result.success(goodsInfoService.getById(goodsId));
    }

}
