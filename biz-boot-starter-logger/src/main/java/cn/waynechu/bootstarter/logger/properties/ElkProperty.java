package cn.waynechu.bootstarter.logger.properties;

import cn.waynechu.bootstarter.logger.properties.nested.KafkaProperty;
import cn.waynechu.bootstarter.logger.properties.nested.RabbitmqProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author zhuwei
 * @since 2019/4/29 17:40
 */
@Data
@ConfigurationProperties(prefix = ElkProperty.ELK_CONFIG_PREFIX)
public class ElkProperty {
    public static final String ELK_CONFIG_PREFIX = "elk";

    /**
     * rabbitmq配置
     */
    @NestedConfigurationProperty
    private RabbitmqProperty rabbitmq;

    /**
     * kafka配置
     */
    @NestedConfigurationProperty
    private KafkaProperty kafka;
}
