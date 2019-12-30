package cn.waynechu.bootstarter.logger;

import cn.waynechu.bootstarter.logger.aware.SentryContextAware;
import cn.waynechu.bootstarter.logger.filter.MDCFilter;
import cn.waynechu.bootstarter.logger.interceptor.FeignTraceInterceptor;
import cn.waynechu.bootstarter.logger.interceptor.RestTemplateTraceInterceptor;
import cn.waynechu.bootstarter.logger.properties.ElkProperty;
import cn.waynechu.bootstarter.logger.properties.LoggerProperty;
import cn.waynechu.bootstarter.logger.properties.SentryProperties;
import cn.waynechu.bootstarter.logger.provider.ApplicationProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author zhuwei
 * @date 2019/1/8 13:50
 */
@Configuration
@EnableConfigurationProperties({SentryProperties.class, ElkProperty.class, LoggerProperty.class})
@Import({ApplicationProvider.class})
public class LoggerAutoConfiguration {

    private static final String NEED_TRACE_HEADERS_STR = "requestId,traceNo,traceAppIds,traceAppNames,traceHostNames,traceHostAddresses";
    private static final List<String> NEED_TRACE_HEADERS = Arrays.asList(NEED_TRACE_HEADERS_STR.split(","));

    @Bean
    @ConditionalOnProperty(value = SentryContextAware.SENTRY_ENABLE, havingValue = "true")
    public SentryContextAware sentryInitializer() {
        return new SentryContextAware();
    }

    @Bean
    public FilterRegistrationBean<MDCFilter> mdcFilterRegistrationBean() {
        FilterRegistrationBean<MDCFilter> registrationBean = new FilterRegistrationBean<>();
        MDCFilter mdcFilter = new MDCFilter();

        registrationBean.setFilter(mdcFilter);
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FeignTraceInterceptor feignInterceptor() {
        return new FeignTraceInterceptor(NEED_TRACE_HEADERS);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        RestTemplateTraceInterceptor traceInterceptor = new RestTemplateTraceInterceptor(NEED_TRACE_HEADERS);
        restTemplate.setInterceptors(Collections.singletonList(traceInterceptor));
        return restTemplate;
    }
}
