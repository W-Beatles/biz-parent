package cn.waynechu.bootstarter.sentry.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhuwei
 * @date 2019/4/29 17:40
 */
@Data
@ConfigurationProperties(prefix = ElkProperty.ELK_CONFIG_PREFIX)
public class ElkProperty {
    public static final String ELK_CONFIG_PREFIX = "elk";

    private Boolean enable = false;
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String applicationId;
    private String virtualHost;
    private String exchange;
    private String routingKey;
    private String clientProvidedName;
}
