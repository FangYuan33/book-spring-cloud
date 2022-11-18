package com.cloud.mall.fy.orderservice.controller;

import com.cloud.mall.fy.orderservice.controller.param.UserAddressAddParam;
import com.cloud.mall.fy.orderservice.controller.param.UserAddressUpdateParam;
import com.cloud.mall.fy.orderservice.service.UserAddressService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "v1", tags = "新蜂商城个人地址相关接口")
@RequestMapping("/order/userAddress")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/list")
    @ApiOperation(value = "我的收货地址列表")
    public AjaxResult addressList() {
        return AjaxResult.success(userAddressService.list());
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加地址")
    public AjaxResult saveUserAddress(@RequestBody @Validated UserAddressAddParam addressAddParam) {
        userAddressService.saveUserAddress(addressAddParam);
        return AjaxResult.success();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改地址")
    public AjaxResult updateUserAddress(@RequestBody @Validated UserAddressUpdateParam addressUpdateParam) {
        userAddressService.updateUserAddress(addressUpdateParam);
        return AjaxResult.success();
    }

    @GetMapping("/{addressId}")
    @ApiOperation(value = "获取收货地址详情", notes = "传参为地址id")
    public AjaxResult getUserAddress(@PathVariable("addressId") Long addressId) {
        return AjaxResult.success(userAddressService.selectById(addressId));
    }

    @GetMapping("/default")
    @ApiOperation(value = "获取默认收货地址")
    public AjaxResult getDefaultUserAddress() {
        return AjaxResult.success(userAddressService.getDefaultUserAddress());
    }

    @DeleteMapping("/delete/{addressId}")
    @ApiOperation(value = "删除收货地址", notes = "传参为地址id")
    public AjaxResult deleteAddress(@PathVariable("addressId") Long addressId) {
        userAddressService.removeById(addressId);
        return AjaxResult.success();
    }
}
