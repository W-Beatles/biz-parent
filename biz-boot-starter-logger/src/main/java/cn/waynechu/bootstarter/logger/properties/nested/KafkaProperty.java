package cn.waynechu.bootstarter.logger.properties.nested;

import lombok.Data;

/**
 * @author zhuwei
 * @since 2019/10/31 15:46
 */
@Data
public class KafkaProperty {

    /**
     * 是否开启日志上传
     */
    private Boolean enable = false;

    private String host;

    private Integer port;

    private String topic;

    private Long bufferMemory;

    private String defaultHost;

    private Integer acks;

    private Integer lingerMs;

    private Integer maxBlockMs;

    private String compressionType;

    private Integer retries;
}
