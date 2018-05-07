package com.yi.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * Created by caihongwei on 2018/5/7 14:38.
 */
@Configuration
public class MyFilter implements GlobalFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyFilter.class);
    private static final String GATE_WAY_PREFIX = "/api";

    @Value("${gate.ignore.startWith:none}")
    private String startWith;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LOGGER.info("check token and user permission....");
        LinkedHashSet requiredAttribute = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        ServerHttpRequest request = exchange.getRequest();
        String requestUri = request.getPath().pathWithinApplication().value();
        if (requiredAttribute != null) {
            Iterator<URI> iterator = requiredAttribute.iterator();
            while (iterator.hasNext()){
                URI next = iterator.next();
                if(next.getPath().startsWith(GATE_WAY_PREFIX)){
                    requestUri = next.getPath().substring(GATE_WAY_PREFIX.length());
                }
            }
        }
        final String method = request.getMethod().toString();
//        BaseContextHandler.setToken(null);
        ServerHttpRequest.Builder mutate = request.mutate();
        // 不进行拦截的地址
        if (isStartWith(requestUri)) {
            ServerHttpRequest build = mutate.build();
            return chain.filter(exchange.mutate().request(build).build());
        }

        if (1==1) {
//            return getVoidMono(exchange);
            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
            return exchange.getResponse().writeWith(Flux.just());
        }

        ServerHttpRequest build = mutate.build();
        return chain.filter(exchange.mutate().request(build).build());
    }

    /**
     * 网关抛异常
     */
    private Mono<Void> getVoidMono(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        byte[] bytes = "Error".getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }

    /**
     * URI是否以什么打头
     *
     * @param requestUri
     * @return
     */
    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : startWith.split(",")) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return flag;
    }
}
