package cn.waynechu.bootstarter.logger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhuwei
 * @since 2019/7/10 20:34
 */
@Data
@ConfigurationProperties(prefix = LoggerProperty.LOGGER_PREFIX)
public class LoggerProperty {

    public static final String LOGGER_PREFIX = "biz.logger.version";

    /**
     * 指定banner展示的 SpringCloud 版本
     */
    private String springCloudDependencies;

    /**
     * 指定banner展示的父项目版本
     */
    private String parentProject;
}
