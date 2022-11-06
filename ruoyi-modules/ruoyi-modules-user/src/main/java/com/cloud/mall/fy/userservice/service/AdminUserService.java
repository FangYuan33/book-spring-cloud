package com.cloud.mall.fy.userservice.service;

import com.cloud.mall.fy.userservice.controller.param.UpdateAdminParam;
import com.cloud.mall.fy.userservice.entity.AdminUser;

public interface AdminUserService {

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
}
