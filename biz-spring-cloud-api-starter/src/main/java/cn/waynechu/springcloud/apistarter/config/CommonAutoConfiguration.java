package cn.waynechu.springcloud.apistarter.config;

import cn.waynechu.springcloud.apistarter.advice.ControllerExceptionHandler;
import cn.waynechu.springcloud.apistarter.aspect.ControllerLogAspect;
import cn.waynechu.springcloud.apistarter.aspect.DistributedLockAspect;
import cn.waynechu.springcloud.apistarter.aspect.MethodLogAspect;
import cn.waynechu.springcloud.apistarter.cache.DefaultBloomOperations;
import cn.waynechu.springcloud.apistarter.cache.RedisUtil;
import cn.waynechu.springcloud.apistarter.interceptor.FeignTraceInterceptor;
import cn.waynechu.springcloud.apistarter.interceptor.RestTemplateTraceInterceptor;
import cn.waynechu.springcloud.apistarter.properties.CommonProperty;
import cn.waynechu.springcloud.common.excel.ExcelExporter;
import cn.waynechu.springcloud.common.util.PageLoopUtil;
import cn.waynechu.springcloud.common.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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
        DistributedLockAspect.class})
public class CommonAutoConfiguration {

    @Autowired
    private CommonProperty commonProperty;

    @Bean
    public SpringContextHolder contextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        RestTemplateTraceInterceptor traceInterceptor = new RestTemplateTraceInterceptor(commonProperty.getNeedTraceHeaders());
        restTemplate.setInterceptors(Collections.singletonList(traceInterceptor));
        return restTemplate;
    }

    @Bean
    public FeignTraceInterceptor feignInterceptor() {
        return new FeignTraceInterceptor(commonProperty.getNeedTraceHeaders());
    }

    @Bean
    public RedisUtil redisUtil(StringRedisTemplate stringRedisTemplate) {
        DefaultBloomOperations<String, String> bloomOperations = new DefaultBloomOperations<>(stringRedisTemplate);
        return new RedisUtil(stringRedisTemplate, bloomOperations);
    }

    @Bean
    public PageLoopUtil pageLoopHelper(Executor bizExecutor) {
        return new PageLoopUtil(bizExecutor);
    }

    @Bean
    public ExcelExporter excelUtil(Executor bizExecutor, RedisTemplate<Object, Object> redisTemplate) {
        return new ExcelExporter(bizExecutor, redisTemplate);
    }
}
