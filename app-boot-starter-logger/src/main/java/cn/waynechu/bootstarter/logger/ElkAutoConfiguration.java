package cn.waynechu.bootstarter.logger;

import cn.waynechu.bootstarter.logger.properties.ElkProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuwei
 * @date 2019/4/29 17:54
 */
@Configuration
@EnableConfigurationProperties({ElkProperty.class})
public class ElkAutoConfiguration {
}
