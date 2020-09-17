package cn.waynechu.bootstarter.sequence.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author zhuwei
 * @since 2020/6/17 16:36
 */
@Data
@ConfigurationProperties(prefix = SequenceProperty.SEQUENCE_PREFIX)
public class SequenceProperty {

    public static final String SEQUENCE_PREFIX = "sequence";

    /**
     * 是否开启sequence服务
     */
    private boolean enable;

    /**
     * 分组名字，默认default
     */
    private String group = "default";

    /**
     * zk节点信息本地缓存文件路径
     */
    private String registryFile;

    /**
     * zk节点是否持久化存储
     */
    private boolean durable;

    /**
     * zk配置信息
     */
    @NestedConfigurationProperty
    private ZookeeperProperty zookeeper = new ZookeeperProperty();
}
