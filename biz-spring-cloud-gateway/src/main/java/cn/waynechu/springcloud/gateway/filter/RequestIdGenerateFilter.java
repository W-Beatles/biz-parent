package cn.waynechu.springcloud.gateway.filter;

import cn.waynechu.bootstarter.logger.filter.MDCFilter;
import cn.waynechu.bootstarter.logger.provider.ApplicationProvider;
import cn.waynechu.springcloud.common.util.StringUtil;
import cn.waynechu.springcloud.common.util.UUIDUtil;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zhuwei
 * @date 2019/9/24 17:49
 */
@Component
@Order(1)
public class RequestIdGenerateFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 网关产生requestId
        String requestId = request.getHeaders().getFirst(MDCFilter.HEADER_KEY_REQUEST_ID);
        if (StringUtil.isBlank(requestId)) {
            requestId = UUIDUtil.getShortUUID();
        }
        MDC.put(MDCFilter.HEADER_KEY_REQUEST_ID, requestId);

        // 初始化trace信息
        String appId = ApplicationProvider.getAppId();
        MDC.put(MDCFilter.HEADER_KEY_TRACE_APP_IDS, appId);
        String appName = ApplicationProvider.getAppName();
        MDC.put(MDCFilter.HEADER_KEY_TRACE_APP_NAMES, appName);
        ServerHttpRequest mutateRequest = request.mutate()
                .header(MDCFilter.HEADER_KEY_REQUEST_ID, requestId)
                .header(MDCFilter.HEADER_KEY_TRACE_APP_IDS, appId)
                .header(MDCFilter.HEADER_KEY_TRACE_APP_NAMES, appName)
                .build();
        ServerWebExchange serverWebExchange = exchange.mutate().request(mutateRequest).build();
        return chain.filter(serverWebExchange);
    }
}
