package com.cloud.mall.fy.goodsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.mall.fy.api.dto.StockNumDto;
import com.cloud.mall.fy.goodsservice.controller.param.GoodsAddParam;
import com.cloud.mall.fy.api.dto.GoodsDetailDto;
import com.cloud.mall.fy.goodsservice.controller.param.GoodsEditParam;
import com.cloud.mall.fy.goodsservice.controller.param.GoodsListParam;
import com.cloud.mall.fy.goodsservice.dao.GoodsInfoMapper;
import com.cloud.mall.fy.goodsservice.entity.GoodsInfo;
import com.cloud.mall.fy.goodsservice.service.GoodsInfoService;
import com.ruoyi.common.core.enums.GoodsSellStatusEnum;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsInfoServiceImpl extends ServiceImpl<GoodsInfoMapper, GoodsInfo> implements GoodsInfoService {

    @Override
    public GoodsDetailDto getById(Long goodsId) {
        if (goodsId == null || goodsId < 0) {
            throw new ServiceException("参数异常");
        }

        GoodsInfo goodsInfo = baseMapper.selectById(goodsId);

        if (goodsInfo == null || GoodsSellStatusEnum.PUT_DOWN.getValue().equals(goodsInfo.getGoodsSellStatus())) {
            throw new ServiceException(GoodsSellStatusEnum.PUT_DOWN.getDesc());
        } else {
            return BeanUtils.copyProperties2(goodsInfo, GoodsDetailDto.class);
        }
    }

    @Override
    public void save(GoodsAddParam goodsAddParam) {
        GoodsInfo goodsInfo = BeanUtils.copyProperties2(goodsAddParam, GoodsInfo.class);
        baseMapper.insert(goodsInfo);
    }

    @Override
    public List<GoodsDetailDto> list(GoodsListParam listParam) {
        LambdaQueryWrapper<GoodsInfo> queryWrapper = new QueryWrapper<GoodsInfo>().lambda()
                .like(StringUtils.isNotEmpty(listParam.getGoodsName()), GoodsInfo::getGoodsName, listParam.getGoodsName())
                .eq(listParam.getGoodsSellStatus() != null, GoodsInfo::getGoodsSellStatus, listParam.getGoodsSellStatus());
        List<GoodsInfo> goodsInfoList = baseMapper.selectList(queryWrapper);

        return BeanUtils.copyList(goodsInfoList,GoodsDetailDto.class);
    }

    @Override
    public void editById(GoodsEditParam goodsEditParam) {
        GoodsInfo update = BeanUtils.copyProperties2(goodsEditParam, GoodsInfo.class);
        update.setId(goodsEditParam.getGoodsId());

        baseMapper.updateById(update);
    }

    @Override
    public boolean batchReduceCount(List<StockNumDto> stockNumDtoList) {
        return baseMapper.updateStockNum(stockNumDtoList) > 0;
    }
}
