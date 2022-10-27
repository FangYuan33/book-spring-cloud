package com.cloud.mall.fy.userservice.controller;

import com.cloud.mall.fy.common.annotation.Log;
import com.cloud.mall.fy.common.annotation.TokenToAdminUser;
import com.cloud.mall.fy.common.dto.Result;
import com.cloud.mall.fy.userservice.controller.param.AdminLoginParam;
import com.cloud.mall.fy.userservice.controller.param.UpdateAdminParam;
import com.cloud.mall.fy.userservice.entity.AdminUser;
import com.cloud.mall.fy.userservice.service.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/user/admin")
@Api(value = "v1", tags = "管理员操作相关接口")
public class AdminUserController {

    @Resource
    private AdminUserService adminUserService;

    @Log
    @PostMapping(value = "/login")
    @ApiOperation(value = "登录接口", notes = "返回token")
    public <T> Result<T> login(@RequestBody @Valid AdminLoginParam adminLoginParam) {
        AdminUser adminUser = adminUserService.login(adminLoginParam.getUserName(), adminLoginParam.getPasswordMd5());

        if (adminUser != null) {
            return Result.success("登录成功");
        } else {
            return Result.fail("登录失败");
        }
    }

    @Log
    @PostMapping("/profile/{adminUserId}")
    @ApiOperation(value = "获取管理员信息接口")
    public Result<AdminUser> profile(@TokenToAdminUser @PathVariable("adminUserId") Long adminUserId) {
        AdminUser adminUser = adminUserService.getUserDetailById(adminUserId);

        if (adminUser != null) {
            return Result.success(adminUser);
        } else {
            return Result.fail("无此用户数据");
        }
    }

    @Log
    @PostMapping("/profile/update")
    @ApiOperation(value = "修改管理员信息接口")
    public <T> Result<T> passwordUpdate(@RequestBody UpdateAdminParam param) {
        adminUserService.updateAdminUser(param);

        return Result.success();
    }

    @Log
    @PostMapping("/logout")
    @ApiOperation(value = "登出接口")
    public <T> Result<T> logout(@RequestBody String token) {
        adminUserService.logout(token);
        return Result.success();
    }

    @Log
    @PostMapping("/{token}")
    @ApiOperation(value = "根据token获取管理员信息接口")
    public Result<AdminUser> getByToken(@PathVariable("token") String token) {
        return Result.success(adminUserService.getByToken(token));
    }
}
