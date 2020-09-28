package cn.waynechu.springcloud.apistarter.config;

import cn.waynechu.springcloud.apistarter.properties.CorsProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

/**
 * 跨域配置
 *
 * @author zhuwei
 * @since 2019/3/27 13:23
 */
@Configuration
@EnableConfigurationProperties({CorsProperty.class})
@ConditionalOnProperty(value = CorsProperty.CORS_CONFIG_PREFIX + ".enable", havingValue = "true")
public class CorsAutoConfiguration implements WebMvcConfigurer {

    @Autowired
    private CorsProperty corsProperty;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins(corsProperty.getAllowedOrigins())
                .allowedHeaders("*")
                .allowedMethods("*")
                // 预检请求有效时间。默认1800
                .maxAge(Duration.ofSeconds(3600).getSeconds());
    }
}
