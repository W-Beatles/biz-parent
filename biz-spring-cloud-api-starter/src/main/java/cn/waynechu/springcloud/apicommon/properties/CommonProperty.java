package cn.waynechu.springcloud.apicommon.properties;

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

    @NestedConfigurationProperty
    private MDCProperty mdcFilter;

    @NestedConfigurationProperty
    private DistributedLockProperty distributedLock;
}
