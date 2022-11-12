package com.cloud.mall.fy.goodsservice.controller;

import com.cloud.mall.fy.api.dto.GoodsDetailDto;
import com.cloud.mall.fy.goodsservice.controller.param.GoodsAddParam;
import com.cloud.mall.fy.goodsservice.controller.param.GoodsEditParam;
import com.cloud.mall.fy.goodsservice.controller.param.GoodsListParam;
import com.cloud.mall.fy.goodsservice.service.GoodsInfoService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.security.annotation.InnerAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/info")
@Api(value = "v1", tags = "商品信息页面接口")
public class GoodsInfoController extends BaseController {

    @Autowired
    private GoodsInfoService goodsInfoService;

    @ApiOperation(value = "商品信息列表", notes = "商品信息列表")
    @PostMapping("/list")
    public TableDataInfo list(GoodsListParam listParam) {
        startPage();
        return getDataTable(goodsInfoService.list(listParam));
    }

    @ApiOperation(value = "新增商品信息", notes = "新增商品信息")
    @PostMapping("/add")
    public AjaxResult save(@RequestBody @Valid GoodsAddParam goodsAddParam) {
        goodsInfoService.save(goodsAddParam);
        return AjaxResult.success();
    }

    @ApiOperation(value = "修改商品信息", notes = "修改商品信息")
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody @Valid GoodsEditParam goodsEditParam) {
        goodsInfoService.editById(goodsEditParam);
        return AjaxResult.success();
    }

    @GetMapping("/detail/{goodsId}")
    @ApiOperation(value = "商品详情接口", notes = "传参为商品id")
    public AjaxResult goodsDetail(@ApiParam(value = "商品id") @PathVariable("goodsId") Long goodsId) {
        return AjaxResult.success(goodsInfoService.getById(goodsId));
    }

    @InnerAuth
    @GetMapping("/inner/detail/{goodsId}")
    public R<GoodsDetailDto> goodsDetailInner(@PathVariable("goodsId") Long goodsId) {
        return R.ok(goodsInfoService.getById(goodsId));
    }
}
