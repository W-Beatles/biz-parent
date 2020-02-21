package cn.waynechu.springcloud.apistarter.config;

import cn.waynechu.springcloud.apistarter.executor.BizThreadPoolExecutor;
import cn.waynechu.springcloud.apistarter.properties.ExecutorServiceProperty;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zhuwei
 * @date 2019/10/18 16:17
 */
@Configuration
@EnableConfigurationProperties({ExecutorServiceProperty.class})
public class ExecutorServiceAutoConfiguration {

    @Autowired
    private ExecutorServiceProperty property;

    @Bean("defaultThreadPoolExecutor")
    public ThreadPoolExecutor defaultThreadPoolTaskExecutor() {
        return new BizThreadPoolExecutor(
                property.getCorePoolSize(),
                property.getCorePoolSize(),
                property.getKeepAliveTime(),
                property.getUnit(),
                property.getWorkQueue(),
                new ThreadFactoryBuilder().setNameFormat(property.getThreadNamePrefix() + "-%d").build(),
                property.getRejectedExecutionHandler()
        );
    }
}
