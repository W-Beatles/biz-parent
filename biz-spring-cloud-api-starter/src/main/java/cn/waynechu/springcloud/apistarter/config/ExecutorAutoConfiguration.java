package cn.waynechu.springcloud.apistarter.config;

import cn.waynechu.springcloud.apistarter.properties.ExecutorProperty;
import cn.waynechu.springcloud.common.executor.BizThreadPoolExecutor;
import com.alibaba.ttl.threadpool.TtlExecutors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author zhuwei
 * @date 2019/10/18 16:17
 */
@Configuration
@ConditionalOnClass(ThreadPoolTaskExecutor.class)
@EnableConfigurationProperties({ExecutorProperty.class})
@AutoConfigureBefore(TaskExecutionAutoConfiguration.class)
public class ExecutorAutoConfiguration {

    @Autowired
    private ExecutorProperty property;

    @Lazy
    @Bean(name = {"bizTaskExecutor",
            TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME,
            AsyncAnnotationBeanPostProcessor.DEFAULT_TASK_EXECUTOR_BEAN_NAME})
    public Executor applicationTaskExecutor() {
        return buildExecutor(property);
    }

    private static Executor buildExecutor(ExecutorProperty executorProperty) {
        BizThreadPoolExecutor executor = new BizThreadPoolExecutor();
        // 设置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(executorProperty.getThreadNamePrefix());
        // 设置核心线程数
        executor.setCorePoolSize(executorProperty.getCorePoolSize());
        // 设置最大线程数
        executor.setMaxPoolSize(executorProperty.getMaxPoolSize());
        // 设置队列大小
        executor.setQueueCapacity(executorProperty.getQueueCapacity());
        // 设置线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(executorProperty.getKeepAliveSeconds());
        // 设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(executorProperty.getIsWaitForTasksToCompleteOnShutdown());
        // 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁
        executor.setAwaitTerminationSeconds(executorProperty.getAwaitTerminationSeconds());
        // 饱和策略。默认使用终止策略，线程池队列满会抛出RejectedExecutionException异常
        executor.setRejectedExecutionHandler(executorProperty.getRejectedExecutionHandler());

        executor.initialize();
        return TtlExecutors.getTtlExecutor(executor);
    }

}
