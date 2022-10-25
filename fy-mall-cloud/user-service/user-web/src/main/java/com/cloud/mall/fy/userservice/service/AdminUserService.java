package com.cloud.mall.fy.userservice.service;

import com.cloud.mall.fy.userservice.controller.param.UpdateAdminParam;
import com.cloud.mall.fy.userservice.entity.AdminUser;

public interface AdminUserService {

    /**
     * 登录方法
     *
     * @param password 未MD5加密的密码
     */
    AdminUser login(String userName, String password);

    /**
     * 获取用户信息
     */
    AdminUser getUserDetailById(Long loginUserId);

    /**
     * 根据ID修改密码或昵称
     *
     * @param param 其中ID必填
     */
    void updateAdminUser(UpdateAdminParam param);

    /**
     * 登出
     */
    void logout(String token);

    /**
     * 根据token获取管理员用户信息
     */
    AdminUser getByToken(String token);
}
