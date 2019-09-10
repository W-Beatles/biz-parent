package cn.waynechu.bootstarter.logger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhuwei
 * @date 2019/7/10 20:34
 */
@Data
@ConfigurationProperties(prefix = BannerProperty.BANNER_CONFIG_PREFIX)
public class BannerProperty {

    public static final String BANNER_CONFIG_PREFIX = "banner.version";

    /**
     * 指定banner展示的 SpringCloud 版本
     */
    private String springCloudDependencies;

    /**
     * 指定banner展示的  biz-spring-cloud-api-starter 版本
     */
    private String bizSpringCloudApiStarter;
}
