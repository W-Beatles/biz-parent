package cn.waynechu.springcloud.apistarter.properties;

import cn.waynechu.springcloud.apistarter.properties.nested.DistributedLockProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhuwei
 * @date 2018/12/28 16:17
 */
@Data
@ConfigurationProperties(prefix = CommonProperty.COMMON_CONFIG_PREFIX)
public class CommonProperty {
    public static final String COMMON_CONFIG_PREFIX = "biz.api.starter";

    /**
     * 调用链路追踪需要传递的请求头。兼容restTemplate和feign两种调用方式
     */
    public List<String> needTraceHeaders = Arrays.asList(
            "request-id", "sc-client-ip", "origin-url",
            "trace-app-ids", "trace-app-names", "trace-host-names", "trace-host-addresses");

    /**
     * 分布式锁配置
     */
    @NestedConfigurationProperty
    private DistributedLockProperty distributedLock;
}
