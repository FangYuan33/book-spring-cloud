package com.cloud.mall.fy.common.utils;

import com.cloud.mall.fy.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BeanUtils {

    /**
     * copy目的类型的集合对象
     *
     * @param sources 原集合
     * @param clazz   目的集合类
     */
    public static <T> List<T> copyList(List<?> sources, Class<T> clazz) {
        List<T> targetList = new ArrayList<>();
        if (sources != null) {
            for (Object source : sources) {
                T target = copyProperties(source, clazz);
                targetList.add(target);
            }
        }
        return targetList;
    }

    /**
     * 赋值出一个目的类的对象
     *
     * @param source           原对象
     * @param targetClass      目的类
     * @param ignoreProperties 忽略的字段
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass, String... ignoreProperties) {
        try {
            T result = targetClass.newInstance();
            if (source != null) {
                org.springframework.beans.BeanUtils.copyProperties(source, result, ignoreProperties);
            }

            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }
}
