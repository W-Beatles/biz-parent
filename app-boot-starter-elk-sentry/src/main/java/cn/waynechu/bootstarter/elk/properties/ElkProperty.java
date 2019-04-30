package cn.waynechu.bootstarter.elk.properties;

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

    /**
     * 是否开启ELK日志上传
     */
    private Boolean enable = false;

    /**
     * RabbitMQ host
     */
    private String host;

    /**
     * RabbitMQ port
     */
    private Integer port;

    /**
     * RabbitMQ username
     */
    private String username;

    /**
     * RabbitMQ password
     */
    private String password;

    /**
     * RabbitMQ virtualHost
     */
    private String virtualHost;

    /**
     * RabbitMQ exchange
     */
    private String exchange;

    /**
     * RabbitMQ routingKey
     */
    private String routingKey;

    /**
     * 应用ID
     */
    private String applicationId;

    /**
     * 客户端标识(在Rabbit Admin界面展示)
     */
    private String connectionName;
}
