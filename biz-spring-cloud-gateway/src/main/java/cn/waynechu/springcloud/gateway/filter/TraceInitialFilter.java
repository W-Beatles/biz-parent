package cn.waynechu.springcloud.gateway.filter;

import cn.waynechu.bootstarter.logger.filter.MDCFilter;
import cn.waynechu.bootstarter.logger.provider.ApplicationProvider;
import cn.waynechu.springcloud.common.util.StringUtil;
import cn.waynechu.springcloud.common.util.UUIDUtil;
import cn.waynechu.springcloud.gateway.util.IpReactiveUtils;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 初始化requestId以及trace信息过滤器
 *
 * @author zhuwei
 * @date 2019/9/24 17:49
 */
@Component
@Order(1)
public class TraceInitialFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 网关产生requestId
        String requestId = request.getHeaders().getFirst(MDCFilter.HEADER_KEY_REQUEST_ID);
        if (StringUtil.isBlank(requestId)) {
            requestId = UUIDUtil.getShortUUID();
        }
        MDC.put(MDCFilter.HEADER_KEY_REQUEST_ID, requestId);

        // 添加客户端ip
        String scClientIp = IpReactiveUtils.getIpAddr(request);
        MDC.put(MDCFilter.HEADER_KEY_SC_CLIENT_IP, scClientIp);

        // 添加trace信息
        String appId = ApplicationProvider.getAppId();
        MDC.put(MDCFilter.HEADER_KEY_TRACE_APP_IDS, appId);
        String appName = ApplicationProvider.getAppName();
        MDC.put(MDCFilter.HEADER_KEY_TRACE_APP_NAMES, appName);
        String hostName = ApplicationProvider.getHostName();
        MDC.put(MDCFilter.HEADER_KEY_TRACE_HOST_NAMES, hostName);
        String hostAddress = ApplicationProvider.getHostAddress();
        MDC.put(MDCFilter.HEADER_KEY_TRACE_HOST_ADDRESSES, hostAddress);

        // 添加来源url信息
        Map<String, Object> attributes = exchange.getAttributes();
        Object originalUrlObj = attributes.get(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        String originalUrl = originalUrlObj.toString().split(",")[0].substring(1);

        // 添加请求头trace信息
        ServerHttpRequest mutateRequest = request.mutate()
                .header(MDCFilter.HEADER_KEY_REQUEST_ID, requestId)
                .header(MDCFilter.HEADER_KEY_SC_CLIENT_IP, scClientIp)
                .header(MDCFilter.HEADER_KEY_ORIGIN_URL, originalUrl)
                .header(MDCFilter.HEADER_KEY_TRACE_APP_IDS, appId)
                .header(MDCFilter.HEADER_KEY_TRACE_APP_NAMES, appName)
                .header(MDCFilter.HEADER_KEY_TRACE_HOST_NAMES, hostName)
                .header(MDCFilter.HEADER_KEY_TRACE_HOST_ADDRESSES, hostAddress)
                .build();
        ServerWebExchange serverWebExchange = exchange.mutate().request(mutateRequest).build();
        return chain.filter(serverWebExchange);
    }
}
