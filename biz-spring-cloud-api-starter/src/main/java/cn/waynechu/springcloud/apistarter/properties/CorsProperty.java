package cn.waynechu.springcloud.apistarter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhuwei
 * @date 2019/4/23 17:00
 */
@Data
@ConfigurationProperties(prefix = CorsProperty.CORS_CONFIG_PREFIX)
public class CorsProperty {
    public static final String CORS_CONFIG_PREFIX = CommonProperty.COMMON_CONFIG_PREFIX + ".cors";

    /**
     * 是否开启跨域设置。默认关闭
     */
    private boolean enable = false;

    /**
     * <pre>
     * 允许跨域URL列表
     * 例如: {@code "http://domain1.com"}, 或者 {@code "*"} 允许所有跨域
     * 匹配的域名会在响应头上加上 {@code Access-Control-Allow-Origin}
     * </pre>
     */
    private String[] allowedOrigins;
}
