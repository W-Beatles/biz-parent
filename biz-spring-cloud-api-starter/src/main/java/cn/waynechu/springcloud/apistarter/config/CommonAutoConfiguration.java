package cn.waynechu.springcloud.apistarter.config;

import cn.waynechu.springcloud.apistarter.advice.ControllerExceptionHandler;
import cn.waynechu.springcloud.apistarter.aspect.ControllerLogAspect;
import cn.waynechu.springcloud.apistarter.aspect.DistributedLockAspect;
import cn.waynechu.springcloud.apistarter.aspect.MethodLogAspect;
import cn.waynechu.springcloud.apistarter.cache.RedisCache;
import cn.waynechu.springcloud.apistarter.mdc.MDCFilter;
import cn.waynechu.springcloud.apistarter.properties.CommonProperty;
import cn.waynechu.springcloud.apistarter.properties.MDCProperty;
import cn.waynechu.springcloud.common.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhuwei
 * @date 2018/12/28 16:16
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({CommonProperty.class})
@Import({ControllerExceptionHandler.class, ControllerLogAspect.class, MethodLogAspect.class,
        DistributedLockAspect.class, RedisCache.class})
public class CommonAutoConfiguration {

    @Bean
    @ConditionalOnProperty(value = MDCProperty.MDC_CONFIG_PREFIX + ".enable", havingValue = "true")
    public FilterRegistrationBean<MDCFilter> mdcFilterRegistrationBean() {
        FilterRegistrationBean<MDCFilter> registrationBean = new FilterRegistrationBean<>();
        MDCFilter mdcFilter = new MDCFilter();

        registrationBean.setFilter(mdcFilter);
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public SpringContextHolder contextHolder() {
        return new SpringContextHolder();
    }
}
