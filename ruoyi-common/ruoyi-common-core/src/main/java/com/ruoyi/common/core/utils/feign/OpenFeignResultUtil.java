package com.ruoyi.common.core.utils.feign;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;

/**
 * OpenFeign调用结果处理工具类
 *
 * @author FangYuan
 * @since 2022-11-15 22:48:41
 */
public class OpenFeignResultUtil {

    /**
     * 处理FeignResult 返回Object对象强转一下即可
     */
    public static Object processFeignResult(R<?> result) {
        if (StringUtils.isNull(result)) {
            throw new ServiceException("服务调用异常");
        }
        if (R.FAIL == result.getCode()) {
            throw new ServiceException(result.getMsg());
        }

        return result.getData();
    }
}
