package com.cloud.mall.fy.shoppingcartservice.controller;

import com.cloud.mall.fy.api.dto.ShoppingCartItemDto;
import com.cloud.mall.fy.shoppingcartservice.controller.param.SaveCartItemParam;
import com.cloud.mall.fy.shoppingcartservice.controller.param.UpdateCartItemParam;
import com.cloud.mall.fy.shoppingcartservice.service.ShoppingCartService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.security.annotation.InnerAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(value = "v1", tags = "购物车相关接口")
@RequestMapping("/shopping-cart")
public class ShoppingCartController extends BaseController {

    @Resource
    private ShoppingCartService shoppingCartService;

    @GetMapping
    @ApiOperation(value = "购物车列表")
    public TableDataInfo cartItemPageList() {
        startPage();
        return getDataTable(shoppingCartService.listByCondition());
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加商品到购物车接口", notes = "传参为商品id、数量")
    public AjaxResult addShoppingCartItem(@RequestBody SaveCartItemParam saveCartItemParam) {
        shoppingCartService.add(saveCartItemParam);
        return AjaxResult.success();
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改购物车商品数量接口", notes = "传参为商品id、数量")
    public AjaxResult updateShoppingCartItem(@RequestBody UpdateCartItemParam updateCartItemParam) {
        shoppingCartService.updateShoppingCartItem(updateCartItemParam);
        return AjaxResult.success();
    }

    @DeleteMapping("/delete/{goodsId}")
    @ApiOperation(value = "删除购物项", notes = "传参为购物项id")
    public AjaxResult deleteById(@PathVariable Long goodsId) {
        shoppingCartService.removeByGoodsId(goodsId);

        return AjaxResult.success();
    }
    @InnerAuth
    @PostMapping("/inner/delete")
    @ApiOperation(value = "删除购物项（内部调用）", notes = "传参为购物项id")
    public R<Boolean> deleteShoppingCartItem(@RequestBody List<Long> shoppingCartItemIdList) {
        if (shoppingCartItemIdList.isEmpty()) {
            return R.fail(false);
        } else {
            return R.ok(shoppingCartService.removeBatchByIds(shoppingCartItemIdList));
        }
    }

    @InnerAuth
    @PostMapping("/inner/listByIds")
    @ApiOperation(value = "根据购物项id数组查询购物项明细（内部调用）", notes = "确认订单页面使用")
    public R<List<ShoppingCartItemDto>> toSettle(@RequestBody List<Long> cartItemIds) {
        return R.ok(shoppingCartService.listByItemIds(cartItemIds));
    }
}
