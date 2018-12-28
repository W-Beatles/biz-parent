package cn.waynechu.app.boot.starter.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhuwei
 * @date 2018/12/28 16:17
 */
@Data
@ConfigurationProperties(prefix = "common")
public class CommonProperties {

    /**
     * redis存储前缀
     */
    private String redisKeyPrefix;

    /**
     * MDC前缀
     */
    private String mdcPrefix;
}
