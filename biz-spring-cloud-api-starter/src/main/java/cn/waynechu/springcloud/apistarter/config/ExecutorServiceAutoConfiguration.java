package cn.waynechu.springcloud.apistarter.config;

import cn.waynechu.springcloud.apistarter.executor.BizThreadPoolTaskExecutor;
import cn.waynechu.springcloud.apistarter.properties.ExecutorServiceProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author zhuwei
 * @date 2019/10/18 16:17
 */
@Configuration
@EnableConfigurationProperties({ExecutorServiceProperty.class})
public class ExecutorServiceAutoConfiguration {

    @Autowired
    private ExecutorServiceProperty property;

    @Bean("defaultThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor defaultThreadPoolTaskExecutor() {
        BizThreadPoolTaskExecutor taskExecutor = new BizThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(property.getCorePoolSize());
        taskExecutor.setMaxPoolSize(property.getMaxPoolSize());
        taskExecutor.setKeepAliveSeconds(property.getKeepAliveSeconds());
        taskExecutor.setQueueCapacity(property.getQueueCapacity());
        taskExecutor.setAwaitTerminationSeconds(property.getAwaitTerminationSeconds());
        taskExecutor.setThreadNamePrefix(property.getThreadNamePrefix());
        taskExecutor.setRejectedExecutionHandler(property.getRejectedExecutionHandler());
        taskExecutor.initialize();
        return taskExecutor;
    }
}
