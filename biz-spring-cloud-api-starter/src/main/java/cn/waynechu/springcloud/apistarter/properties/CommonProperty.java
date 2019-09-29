package cn.waynechu.springcloud.apistarter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author zhuwei
 * @date 2018/12/28 16:17
 */
@Data
@ConfigurationProperties(prefix = CommonProperty.COMMON_CONFIG_PREFIX)
public class CommonProperty {
    public static final String COMMON_CONFIG_PREFIX = "biz.api.starter";

    /**
     * MDC过滤器配置
     */
    @NestedConfigurationProperty
    private MDCProperty mdcFilter;

    /**
     * Feign请求头拦截器配置
     */
    @NestedConfigurationProperty
    private FeignTraceProperty feignTraceInterceptor;

    /**
     * 分布式锁配置
     */
    @NestedConfigurationProperty
    private DistributedLockProperty distributedLock;
}
