package cn.waynechu.springcloud.gateway.filter;

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

import static cn.waynechu.bootstarter.logger.constant.TraceKeyConstant.HEADER_KEY_ORIGIN_URL;
import static cn.waynechu.bootstarter.logger.constant.TraceKeyConstant.HEADER_KEY_REQUEST_ID;
import static cn.waynechu.bootstarter.logger.constant.TraceKeyConstant.HEADER_KEY_SC_CLIENT_IP;
import static cn.waynechu.bootstarter.logger.constant.TraceKeyConstant.HEADER_KEY_TRACE_APP_IDS;
import static cn.waynechu.bootstarter.logger.constant.TraceKeyConstant.HEADER_KEY_TRACE_APP_NAMES;
import static cn.waynechu.bootstarter.logger.constant.TraceKeyConstant.HEADER_KEY_TRACE_HOST_ADDRESSES;
import static cn.waynechu.bootstarter.logger.constant.TraceKeyConstant.HEADER_KEY_TRACE_HOST_NAMES;
import static cn.waynechu.bootstarter.logger.constant.TraceKeyConstant.MDC_KEY_REQUEST_ID;
import static cn.waynechu.bootstarter.logger.constant.TraceKeyConstant.MDC_KEY_SC_CLIENT_IP;
import static cn.waynechu.bootstarter.logger.constant.TraceKeyConstant.MDC_KEY_TRACE_APP_IDS;
import static cn.waynechu.bootstarter.logger.constant.TraceKeyConstant.MDC_KEY_TRACE_APP_NAMES;
import static cn.waynechu.bootstarter.logger.constant.TraceKeyConstant.MDC_KEY_TRACE_HOST_ADDRESSES;
import static cn.waynechu.bootstarter.logger.constant.TraceKeyConstant.MDC_KEY_TRACE_HOST_NAMES;

/**
 * 初始化requestId以及trace信息过滤器
 *
 * @author zhuwei
 * @since 2019/9/24 17:49
 */
@Component
@Order(1)
public class TraceInitialFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 网关产生requestId
        String requestId = request.getHeaders().getFirst(HEADER_KEY_REQUEST_ID);
        if (StringUtil.isBlank(requestId)) {
            requestId = UUIDUtil.getShortUUID();
        }
        MDC.put(MDC_KEY_REQUEST_ID, requestId);

        // 添加客户端ip
        String scClientIp = IpReactiveUtils.getIpAddr(request);
        MDC.put(MDC_KEY_SC_CLIENT_IP, scClientIp);

        // 添加trace信息
        String appId = ApplicationProvider.getAppId();
        MDC.put(MDC_KEY_TRACE_APP_IDS, appId);
        String appName = ApplicationProvider.getAppName();
        MDC.put(MDC_KEY_TRACE_APP_NAMES, appName);
        String hostName = ApplicationProvider.getHostName();
        MDC.put(MDC_KEY_TRACE_HOST_NAMES, hostName);
        String hostAddress = ApplicationProvider.getHostAddress();
        MDC.put(MDC_KEY_TRACE_HOST_ADDRESSES, hostAddress);

        // 添加来源url信息
        String originalUrl = null;
        try {
            Map<String, Object> attributes = exchange.getAttributes();
            Object originalUrlObj = attributes.get(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
            originalUrl = originalUrlObj.toString().split(",")[0].substring(1);
        } catch (Exception e) {
            // do nothing here.
        }

        // 添加请求头trace信息
        ServerHttpRequest mutateRequest = request.mutate()
                .header(HEADER_KEY_REQUEST_ID, requestId)
                .header(HEADER_KEY_SC_CLIENT_IP, scClientIp)
                .header(HEADER_KEY_ORIGIN_URL, originalUrl)
                .header(HEADER_KEY_TRACE_APP_IDS, appId)
                .header(HEADER_KEY_TRACE_APP_NAMES, appName)
                .header(HEADER_KEY_TRACE_HOST_NAMES, hostName)
                .header(HEADER_KEY_TRACE_HOST_ADDRESSES, hostAddress)
                .build();
        ServerWebExchange serverWebExchange = exchange.mutate().request(mutateRequest).build();
        return chain.filter(serverWebExchange);
    }
}
