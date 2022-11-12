package com.cloud.mall.fy.recommendservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.mall.fy.api.dto.CarouselDto;
import com.cloud.mall.fy.recommendservice.dao.CarouselMapper;
import com.cloud.mall.fy.recommendservice.entity.Carousel;
import com.cloud.mall.fy.recommendservice.service.CarouselService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {

    @Override
    public List<CarouselDto> listByCondition() {
        List<Carousel> carouselList = baseMapper.selectList(new QueryWrapper<>());
        return BeanUtils.copyList(carouselList, CarouselDto.class);
    }

    @Override
    public void batchDelete(String batchIds) {
        if (StringUtils.isEmpty(batchIds)) {
            throw new ServiceException("参数为空");
        }

        String[] ids = batchIds.split(",");
        baseMapper.deleteBatchIds(Arrays.asList(ids));
    }
}
