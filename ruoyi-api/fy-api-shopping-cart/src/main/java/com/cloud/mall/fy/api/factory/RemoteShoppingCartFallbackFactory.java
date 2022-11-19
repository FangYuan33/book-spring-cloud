package com.cloud.mall.fy.api.factory;

import com.cloud.mall.fy.api.RemoteShoppingCartService;
import com.cloud.mall.fy.api.dto.ShoppingCartItemDto;
import com.ruoyi.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RemoteShoppingCartFallbackFactory implements FallbackFactory<RemoteShoppingCartService> {

    @Override
    public RemoteShoppingCartService create(Throwable cause) {
        log.error(cause.getMessage(), cause);

        return new RemoteShoppingCartService() {
            @Override
            public R<List<ShoppingCartItemDto>> toSettle(List<Long> cartItemIds, String source) {
                return R.fail("获取购物车信息失败: " + cause.getMessage());
            }

            @Override
            public R<Boolean> deleteShoppingCartItem(List<Long> shoppingCartItemIdList, String source) {
                return R.fail("删除购物车信息失败: " + cause.getMessage());
            }
        };
    }
}
