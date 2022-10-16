package com.example.demogateway.config;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义局部过滤器
 *
 * 命名方式为xxxGatewayFilterFactory，然后在配置文件中写上-xxx
 */
@Component
public class OrderGatewayFilterFactory extends AbstractGatewayFilterFactory<OrderGatewayFilterFactory.Config> {

    public OrderGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // 取值
            String goodsIdParam = exchange.getRequest().getQueryParams().getFirst("goodsId");

            // 判空
            if (StringUtils.hasText(goodsIdParam)) {
                int goodsId = Integer.parseInt(goodsIdParam);
                if (goodsId > config.getMinValue() && goodsId < config.getMaxValue()) {
                    // 判断goodsId是否在配置区间内，直接放行
                    return chain.filter(exchange);
                } else {
                    // 不符合条件，返回错误的提示信息，不进行后续的路由
                    byte[] bytes = ("BAD REQUEST").getBytes(StandardCharsets.UTF_8);
                    DataBuffer wrap = exchange.getResponse().bufferFactory().wrap(bytes);
                    exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                    return exchange.getResponse().writeWith(Flux.just(wrap));
                }
            }
            // 直接放行
            return chain.filter(exchange);
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("minValue", "maxValue");
    }

    @Data
    public static class Config {

        private int minValue;

        private int maxValue;
    }
}
