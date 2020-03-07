package cn.waynechu.springcloud.gateway.util;

import cn.waynechu.springcloud.common.util.CollectionUtil;
import cn.waynechu.springcloud.gateway.constant.ChannelConstant;
import cn.waynechu.springcloud.gateway.enums.AuthTypeEnum;
import cn.waynechu.springcloud.gateway.property.AuthTypeSwitchProperties;
import lombok.experimental.UtilityClass;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;

/**
 * @author zhuwei
 * @date 2020-03-07 16:22
 */
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
}
