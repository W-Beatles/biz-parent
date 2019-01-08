package cn.waynechu.boot.starter.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author zhuwei
 * @date 2018/12/28 16:17
 */
@Data
@ConfigurationProperties(prefix = "common")
public class CommonProperties {

    @NestedConfigurationProperty
    private RedisCacheProperties redisCache;

    @NestedConfigurationProperty
    private MDCProperties mdcFilter;

    @NestedConfigurationProperty
    private DataModifiedProperties dataModifiedInterceptor;
}
