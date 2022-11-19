package com.cloud.mall.fy.orderservice.controller;

import com.cloud.mall.fy.orderservice.controller.param.OrderQueryParam;
import com.cloud.mall.fy.orderservice.service.OrderService;
import com.ruoyi.common.core.enums.OrderStatusEnum;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "v1", tags = "后台管理系统订单模块接口")
@RequestMapping("/order/admin")
public class OrderAdminController extends BaseController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/list")
    @ApiOperation(value = "订单列表", notes = "可根据订单号和订单状态筛选")
    public TableDataInfo list(OrderQueryParam orderQueryParam) {
        startPage();
        return getDataTable(orderService.listByCondition(orderQueryParam));
    }

    @GetMapping("/detail/{orderId}")
    @ApiOperation(value = "订单详情接口", notes = "传参为订单号")
    public AjaxResult orderDetail(@ApiParam(value = "订单号") @PathVariable("orderId") Long orderId) {
        return AjaxResult.success(orderService.getOrderDetailById(orderId));
    }

    @PutMapping(value = "/checkDone")
    @ApiOperation(value = "修改订单状态为配货成功", notes = "批量修改")
    public AjaxResult checkDone(@RequestBody List<Long> idList) {
        orderService.batchChangeStatusFromTo(idList, OrderStatusEnum.ALREADY_PAY, OrderStatusEnum.CHECK_DONE);
        return AjaxResult.success();
    }

    @PutMapping(value = "/checkSend")
    @ApiOperation(value = "修改订单状态为已出库", notes = "批量修改")
    public AjaxResult checkSend(@RequestBody List<Long> idList) {
        orderService.batchChangeStatusFromTo(idList, OrderStatusEnum.CHECK_DONE, OrderStatusEnum.SEND);
        return AjaxResult.success();
    }

    @PutMapping(value = "/close")
    @ApiOperation(value = "修改订单状态为商家关闭", notes = "批量修改")
    public AjaxResult closeOrder(@RequestBody List<Long> idList) {
        orderService.batchChangeStatusFromTo(idList, null, OrderStatusEnum.CLOSED_BY_BUSINESS);
        return AjaxResult.success();
    }
}