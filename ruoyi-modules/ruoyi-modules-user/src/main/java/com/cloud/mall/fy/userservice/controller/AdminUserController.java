package com.cloud.mall.fy.userservice.controller;

import com.cloud.mall.fy.userservice.controller.param.UpdateAdminParam;
import com.cloud.mall.fy.userservice.entity.AdminUser;
import com.cloud.mall.fy.userservice.service.AdminUserService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user/admin")
@Api(value = "v1", tags = "管理员操作相关接口")
public class AdminUserController extends BaseController {

    @Resource
    private AdminUserService adminUserService;

    @PostMapping("/profile/{adminUserId}")
    @ApiOperation(value = "获取管理员信息接口")
    public AjaxResult profile(@PathVariable("adminUserId") Long adminUserId) {
        AdminUser adminUser = adminUserService.getUserDetailById(adminUserId);

        if (adminUser != null) {
            return AjaxResult.success(adminUser);
        } else {
            return AjaxResult.error("无此用户数据");
        }
    }

    @PostMapping("/profile/update")
    @ApiOperation(value = "修改管理员信息接口")
    public AjaxResult passwordUpdate(@RequestBody UpdateAdminParam param) {
        adminUserService.updateAdminUser(param);

        return AjaxResult.success();
    }

}
