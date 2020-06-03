package cn.waynechu.springcloud.gateway.util;

import cn.waynechu.springcloud.common.util.CollectionUtil;
import cn.waynechu.springcloud.common.util.StringUtil;
import cn.waynechu.springcloud.gateway.constant.ChannelConstant;
import cn.waynechu.springcloud.gateway.enums.AuthTypeEnum;
import cn.waynechu.springcloud.gateway.property.AuthTypeSwitchProperties;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author zhuwei
 * @date 2020-03-07 16:22
 */
@Slf4j
@UtilityClass
public class AuthUtil {

    /**
     * 获取authType
     *
     * @param request request
     * @return AuthTypeEnum
     */
    public static AuthTypeEnum getAuthType(ServerHttpRequest request) {
        String authTypeStr = getFromHeader(request, ChannelConstant.AUTH_TYPE_KEY);
        return AuthTypeEnum.getByName(authTypeStr);
    }

    /**
     * 从请求头中获取信息
     *
     * @param request request
     * @param key     请求头key
     * @return 请求头value
     */
    private static String getFromHeader(ServerHttpRequest request, String key) {
        return request.getHeaders().getFirst(key);
    }

    /**
     * 判断鉴权渠道是否开启
     *
     * @param properties 鉴权渠道配置信息
     * @param authType   渠道名称
     * @return true if 开启
     */
    public static boolean isAuthTypeOpened(AuthTypeSwitchProperties properties, String authType) {
        if (properties.getOpenAll()) {
            return true;
        }
        List<String> opens = properties.getOpens();
        return CollectionUtil.isNotNullOrEmpty(opens) && opens.contains(authType);
    }

    /**
     * 网关拒绝，返回401
     *
     * @param serverWebExchange change
     */
    public static Mono<Void> unauthorized(ServerWebExchange serverWebExchange, String message) {
        ServerHttpResponse response = serverWebExchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBuffer buffer = response.bufferFactory()
                .wrap(StringUtil.isBlank(message) ? HttpStatus.UNAUTHORIZED.getReasonPhrase().getBytes()
                        : message.getBytes());
        log.info("网关401: {}", message);
        return response.writeWith(Flux.just(buffer));
    }
}
