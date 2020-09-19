package cn.waynechu.springcloud.apistarter.config;

import cn.waynechu.springcloud.apistarter.advice.ControllerExceptionHandler;
import cn.waynechu.springcloud.apistarter.aspect.ControllerLogAspect;
import cn.waynechu.springcloud.apistarter.aspect.DistributedLockAspect;
import cn.waynechu.springcloud.apistarter.aspect.MethodLogAspect;
import cn.waynechu.springcloud.apistarter.cache.DefaultBloomOperations;
import cn.waynechu.springcloud.apistarter.cache.RedisHelper;
import cn.waynechu.springcloud.apistarter.interceptor.FeignTraceInterceptor;
import cn.waynechu.springcloud.apistarter.interceptor.RestTemplateTraceInterceptor;
import cn.waynechu.springcloud.apistarter.properties.CommonProperty;
import cn.waynechu.springcloud.common.excel.ExcelHelper;
import cn.waynechu.springcloud.common.util.PageLoopHelper;
import cn.waynechu.springcloud.common.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.Executor;

/**
 * @author zhuwei
 * @since 2018/12/28 16:16
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
        // 使用OkHttp替换默认的URLConnection请求工具，提升请求性能
        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory());
        // 添加拦截器用于传递请求头
        RestTemplateTraceInterceptor traceInterceptor = new RestTemplateTraceInterceptor(commonProperty.getNeedTraceHeaders());
        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(traceInterceptor);
        restTemplate.setInterceptors(interceptors);
        // 替换默认的String解码器字符编码，防止调用方解码String类型的返回出现乱码问题
        Optional<HttpMessageConverter<?>> converter = restTemplate.getMessageConverters().stream()
                .filter(e -> e instanceof StringHttpMessageConverter).findAny();
        converter.ifPresent(httpMessageConverter -> ((StringHttpMessageConverter) httpMessageConverter)
                .setDefaultCharset(StandardCharsets.UTF_8));
        return restTemplate;
    }

    @Bean
    public FeignTraceInterceptor feignInterceptor() {
        return new FeignTraceInterceptor(commonProperty.getNeedTraceHeaders());
    }

    @Bean
    public RedisHelper redisHelper(StringRedisTemplate stringRedisTemplate) {
        DefaultBloomOperations<String, String> bloomOperations = new DefaultBloomOperations<>(stringRedisTemplate);
        return new RedisHelper(stringRedisTemplate, bloomOperations);
    }

    @Bean
    public PageLoopHelper pageLoopHelper(Executor bizExecutor) {
        return new PageLoopHelper(bizExecutor);
    }

    @Bean
    public ExcelHelper excelHelper(Executor bizExecutor, StringRedisTemplate redisTemplate) {
        return new ExcelHelper(bizExecutor, redisTemplate, restTemplate());
    }
}
