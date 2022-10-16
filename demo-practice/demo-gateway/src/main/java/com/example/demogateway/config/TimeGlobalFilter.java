package com.example.demogateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 全局过滤器
 */
@Component
public class TimeGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LocalDateTime endTime = LocalDateTime.of(2022, 10, 16, 18, 0);
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(endTime)) {
            return chain.filter(exchange).then(Mono.fromRunnable(() ->
                    System.out.println(exchange.getRequest().getURI().getPath() + " " +
                    "请求时间为 " + now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))));
        } else {
            byte[] bytes = ("It's so latter. Time to eat dinner.").getBytes(StandardCharsets.UTF_8);
            DataBuffer wrap = exchange.getResponse().bufferFactory().wrap(bytes);
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            return exchange.getResponse().writeWith(Flux.just(wrap));
        }
    }

    /**
     * 实现Ordered接口，保证全局过滤器的注册在局部过滤器之前
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
