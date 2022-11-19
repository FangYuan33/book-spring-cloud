package com.cloud.mall.fy.orderservice.controller;

import com.cloud.mall.fy.orderservice.controller.param.OrderPayParam;
import com.cloud.mall.fy.orderservice.controller.param.OrderSaveParam;
import com.cloud.mall.fy.orderservice.service.OrderService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "v1", tags = "新蜂商城订单操作相关接口")
@RequestMapping("/order/mall")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/saveOrder")
    @ApiOperation(value = "生成订单接口", notes = "传参为地址id和待结算的购物项ids")
    public AjaxResult saveOrder(@ApiParam(value = "订单参数") @RequestBody OrderSaveParam orderSaveParam) {
        orderService.saveOrder(orderSaveParam);
        return AjaxResult.success();
    }

    @PutMapping("/cancel/{orderId}")
    @ApiOperation(value = "订单取消接口")
    public AjaxResult cancelOrder(@ApiParam(value = "订单ID") @PathVariable("orderId") Long orderId) {
        orderService.cancelOrderByIda(orderId);

        return AjaxResult.success();
    }

    @PutMapping("/finish/{orderId}")
    @ApiOperation(value = "确认收货接口")
    public AjaxResult finishOrder(@ApiParam(value = "订单ID") @PathVariable("orderId") Long orderId) {
        orderService.finishOrder(orderId);

        return AjaxResult.success();
    }

    @PostMapping("/paySuccess")
    @ApiOperation(value = "模拟支付成功回调的接口")
    public AjaxResult paySuccess(@RequestBody OrderPayParam orderPayParam) {
        orderService.paySuccess(orderPayParam);

        return AjaxResult.success();
    }
}
