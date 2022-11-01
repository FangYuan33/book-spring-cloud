package com.cloud.mall.fy.goodsservice.service.impl;

import com.cloud.mall.fy.goodsservice.dao.GoodsInfoMapper;
import com.cloud.mall.fy.goodsservice.service.GoodsInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GoodsInfoServiceImpl implements GoodsInfoService {

    @Resource
    private GoodsInfoMapper goodsInfoMapper;


}
