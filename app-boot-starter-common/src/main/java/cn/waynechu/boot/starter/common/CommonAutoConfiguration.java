package cn.waynechu.boot.starter.common;

import cn.waynechu.boot.starter.common.aspect.ControllerExceptionHandler;
import cn.waynechu.boot.starter.common.aspect.ControllerLogAspect;
import cn.waynechu.boot.starter.common.aspect.MethodLogAspect;
import cn.waynechu.boot.starter.common.filter.MDCFilter;
import cn.waynechu.boot.starter.common.properties.CommonProperties;
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
@Import({ControllerExceptionHandler.class, ControllerLogAspect.class, MethodLogAspect.class})
@EnableConfigurationProperties({CommonProperties.class})
public class CommonAutoConfiguration {

    @Autowired
    private CommonProperties commonProperties;

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    @ConditionalOnProperty(value = "common.mdc-filter.enable", havingValue = "true")
    public FilterRegistrationBean<MDCFilter> mdcFilterRegistrationBean() {
        FilterRegistrationBean<MDCFilter> registrationBean = new FilterRegistrationBean<>();
        MDCFilter mdcFilter = new MDCFilter();
        String mdcPrefix = commonProperties.getMdcFilter().getPrefix();
        mdcFilter.setPrefix(mdcPrefix == null ? applicationName : mdcPrefix);
        mdcFilter.setApplicationName(applicationName);

        registrationBean.setFilter(mdcFilter);
        registrationBean.setOrder(1);
        return registrationBean;
    }
}