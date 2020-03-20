package cn.waynechu.springcloud.apistarter.config;

import cn.waynechu.springcloud.apistarter.executor.BizThreadPoolExecutor;
import cn.waynechu.springcloud.apistarter.properties.ExecutorProperty;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.Executor;

/**
 * @author zhuwei
 * @date 2019/10/18 16:17
 */
@Configuration
@EnableConfigurationProperties({ExecutorProperty.class})
public class ExecutorAutoConfiguration {

    @Autowired
    private ExecutorProperty property;

    @Primary
    @Bean("defaultThreadPoolExecutor")
    public Executor defaultThreadPoolTaskExecutor() {
        return new BizThreadPoolExecutor();
    }

    private Executor buildExecutor(ExecutorProperty executorProperty) {
        BizThreadPoolExecutor executor = new BizThreadPoolExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(executorProperty.getCorePoolSize());
        // 设置最大线程数
        executor.setMaxPoolSize(executorProperty.getMaxPoolSize());
        // 设置队列大小
        executor.setQueueCapacity(executorProperty.getQueueCapacity());
        // 设置线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(executorProperty.getKeepAliveSeconds());
        // 设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(executorProperties.isWaitForTasksToCompleteOnShutdown());
        // 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁
        executor.setAwaitTerminationSeconds(executorProperties.getAwaitTerminationSeconds());
        // 设置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(executorProperties.getThreadNamePrefix());
        property.getCorePoolSize(),
                property.getCorePoolSize(),
                property.getKeepAliveTime(),
                property.getUnit(),
                property.getWorkQueue(),
                new ThreadFactoryBuilder().setNameFormat(property.getThreadNamePrefix() + "-%d").build(),
                property.getRejectedExecutionHandler()
    }
}
