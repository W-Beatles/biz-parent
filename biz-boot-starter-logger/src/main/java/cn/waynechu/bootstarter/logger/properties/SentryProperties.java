package cn.waynechu.bootstarter.logger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhuwei
 * @since 2019/1/8 12:52
 */
@Data
@ConfigurationProperties(prefix = "sentry")
public class SentryProperties {

    /**
     * 是否开启sentry日志上传
     */
    private boolean enable = false;

    /**
     * sentry dsn 地址
     */
    private String dsn;

    /**
     * 要追踪的包名，多个请用 “,” 分割。默认上传所有包下的日志
     */
    private String stacktraceAppPackages = "";
}
