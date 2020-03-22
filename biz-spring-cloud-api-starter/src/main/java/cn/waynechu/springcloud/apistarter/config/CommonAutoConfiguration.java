package cn.waynechu.springcloud.apistarter.config;

import cn.waynechu.bootstarter.logger.LoggerAutoConfiguration;
import cn.waynechu.bootstarter.logger.interceptor.RestTemplateTraceInterceptor;
import cn.waynechu.springcloud.apistarter.advice.ControllerExceptionHandler;
import cn.waynechu.springcloud.apistarter.aspect.ControllerLogAspect;
import cn.waynechu.springcloud.apistarter.aspect.DistributedLockAspect;
import cn.waynechu.springcloud.apistarter.aspect.MethodLogAspect;
import cn.waynechu.springcloud.apistarter.cache.RedisCache;
import cn.waynechu.springcloud.apistarter.helper.PageLoopHelper;
import cn.waynechu.springcloud.apistarter.properties.CommonProperty;
import cn.waynechu.springcloud.common.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.concurrent.Executor;

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
    public SpringContextHolder contextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        RestTemplateTraceInterceptor traceInterceptor = new RestTemplateTraceInterceptor(LoggerAutoConfiguration.NEED_TRACE_HEADERS);
        restTemplate.setInterceptors(Collections.singletonList(traceInterceptor));
        return restTemplate;
    }

    @Bean
    public PageLoopHelper pageLoopHelper(Executor bizTaskExecutor) {
        return new PageLoopHelper(bizTaskExecutor);
    }
}
