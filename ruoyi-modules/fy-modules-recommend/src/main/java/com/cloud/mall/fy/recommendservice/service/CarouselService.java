package com.cloud.mall.fy.recommendservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.mall.fy.api.dto.CarouselDto;
import com.cloud.mall.fy.recommendservice.entity.Carousel;

import java.util.List;

/**
 * 轮播图服务层
 *
 * @author FangYuan
 * @since 2022-11-12 11:19:46
 */
public interface CarouselService extends IService<Carousel> {

    /**
     * 轮播图列表
     */
    List<CarouselDto> listByCondition();

    /**
     * 批量删除
     *
     * @param batchIds ids, 用英文逗号分割
     */
    void batchDelete(String batchIds);
}
