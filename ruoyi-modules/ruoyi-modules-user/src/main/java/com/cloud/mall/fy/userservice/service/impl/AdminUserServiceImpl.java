package com.cloud.mall.fy.userservice.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cloud.mall.fy.userservice.controller.param.UpdateAdminParam;
import com.cloud.mall.fy.userservice.dao.AdminUserMapper;
import com.cloud.mall.fy.userservice.entity.AdminUser;
import com.cloud.mall.fy.userservice.service.AdminUserService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public AdminUser getUserDetailById(Long loginUserId) {
        return adminUserMapper.selectById(loginUserId);
    }

    @Override
    public void updateAdminUser(UpdateAdminParam param) {
        if (StringUtils.isEmpty(param.getNickName()) && StringUtils.isEmpty(param.getNewPassword())) {
            throw new ServiceException("参数异常");
        }
        if (!StringUtils.isEmpty(param.getNewPassword()) && StringUtils.isEmpty(param.getOriginalPassword())) {
            throw new ServiceException("请输入原始密码");
        }

        doUpdateAdminUser(param);
    }

    private void doUpdateAdminUser(UpdateAdminParam param) {
        UpdateWrapper<AdminUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .set(!StringUtils.isEmpty(param.getNickName()), AdminUser::getNickName, param.getNickName())
                .set(!StringUtils.isEmpty(param.getNewPassword()) && !StringUtils.isEmpty(param.getOriginalPassword()),
                        AdminUser::getLoginPassword, MD5Util.MD5Encode(param.getNewPassword(), "UTF-8"))
                .eq(!StringUtils.isEmpty(param.getOriginalPassword()),
                        AdminUser::getLoginPassword, MD5Util.MD5Encode(param.getOriginalPassword(), "UTF-8"))
                .eq(AdminUser::getId, param.getId());

        adminUserMapper.update(null, updateWrapper);
    }

}