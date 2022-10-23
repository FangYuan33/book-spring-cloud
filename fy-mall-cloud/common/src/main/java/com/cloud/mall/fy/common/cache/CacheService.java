package com.cloud.mall.fy.common.cache;

public interface CacheService {

    /**
     * 保存有时间限制的key
     *
     * @param second 秒数
     */
     void setValue(String key, Object value, Long second);

    /**
     * 根据key删除缓存
     */
    void deleteByKey(String key);
}
