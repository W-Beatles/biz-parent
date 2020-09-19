package cn.waynechu.bootstarter.logger.properties.nested;

import lombok.Data;

/**
 * @author zhuwei
 * @since 2019/10/31 15:45
 */
@Data
public class RabbitmqProperty {

    /**
     * 是否开启日志上传
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
