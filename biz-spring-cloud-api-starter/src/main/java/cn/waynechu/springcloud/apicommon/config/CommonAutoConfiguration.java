package cn.waynechu.springcloud.apicommon.config;

import cn.waynechu.springcloud.apicommon.advice.ControllerExceptionHandler;
import cn.waynechu.springcloud.apicommon.aspect.ControllerLogAspect;
import cn.waynechu.springcloud.apicommon.aspect.DistributedLockAspect;
import cn.waynechu.springcloud.apicommon.aspect.MethodLogAspect;
import cn.waynechu.springcloud.apicommon.cache.RedisCache;
import cn.waynechu.springcloud.apicommon.mdc.MDCFilter;
import cn.waynechu.springcloud.apicommon.properties.CommonProperty;
import cn.waynechu.springcloud.apicommon.properties.DistributedLockProperty;
import cn.waynechu.springcloud.apicommon.properties.MDCProperty;
import cn.waynechu.springcloud.common.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private CommonProperty commonProperty;

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    @ConditionalOnProperty(value = MDCProperty.MDC_CONFIG_PREFIX + ".enable", havingValue = "true")
    public FilterRegistrationBean<MDCFilter> mdcFilterRegistrationBean() {
        FilterRegistrationBean<MDCFilter> registrationBean = new FilterRegistrationBean<>();
        MDCFilter mdcFilter = new MDCFilter();
        String mdcPrefix = commonProperty.getMdcFilter().getPrefix();
        mdcFilter.setPrefix(mdcPrefix == null ? applicationName : mdcPrefix);
        mdcFilter.setApplicationName(applicationName);

        registrationBean.setFilter(mdcFilter);
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public SpringContextHolder contextHolder() {
        return new SpringContextHolder();
    }
}
