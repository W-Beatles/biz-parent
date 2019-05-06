package cn.waynechu.springcloud.apicommon.config;

import cn.waynechu.springcloud.apicommon.properties.CorsProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 *
 * @author zhuwei
 * @date 2019/3/27 13:23
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
                .allowedOrigins(corsProperty.getAllowedOrigins())
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                // 预检请求有效时间。默认1800
                .maxAge(3600);
    }
}
