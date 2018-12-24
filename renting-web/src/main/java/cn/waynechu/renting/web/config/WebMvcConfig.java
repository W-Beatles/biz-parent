package cn.waynechu.renting.web.config;

import cn.waynechu.renting.web.interceptor.SessionInterception;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhuwei
 * @date 2018/12/24 14:00
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public SessionInterception sessionInterception() {
        return new SessionInterception();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterception());
    }
}
