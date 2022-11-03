package com.cloud.mall.fy.goodsservice.service.impl;

import com.cloud.mall.fy.common.enums.GoodsSellStatusEnum;
import com.cloud.mall.fy.common.exception.BusinessException;
import com.cloud.mall.fy.common.utils.BeanUtils;
import com.cloud.mall.fy.goodsservice.controller.vo.GoodsDetailVO;
import com.cloud.mall.fy.goodsservice.dao.GoodsInfoMapper;
import com.cloud.mall.fy.goodsservice.entity.GoodsInfo;
import com.cloud.mall.fy.goodsservice.service.GoodsInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GoodsInfoServiceImpl implements GoodsInfoService {

    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    public GoodsDetailVO getById(Long goodsId) {
        if (goodsId == null || goodsId < 0) {
            throw new BusinessException("参数异常");
        }

        GoodsInfo goodsInfo = goodsInfoMapper.selectById(goodsId);

        if (goodsInfo == null || GoodsSellStatusEnum.PUT_DOWN.getValue().equals(goodsInfo.getGoodsSellStatus())) {
            throw new BusinessException(GoodsSellStatusEnum.PUT_DOWN.getDesc());
        } else {
            return BeanUtils.copyProperties(goodsInfo, GoodsDetailVO.class);
        }
    }
}
