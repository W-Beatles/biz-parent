package cn.waynechu.springcloud.apistarter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zhuwei
 * @date 2019/10/18 16:19
 */
@Data
@ConfigurationProperties(prefix = ExecutorServiceProperty.EXECUTOR_SERVICE_CONFIG_PREFIX)
public class ExecutorServiceProperty {
    public static final String EXECUTOR_SERVICE_CONFIG_PREFIX = CommonProperty.COMMON_CONFIG_PREFIX + ".executor-service";

    /**
     * 核心线程数。默认为服务器处理器个数
     */
    private int corePoolSize = Runtime.getRuntime().availableProcessors();

    /**
     * 最大线程数
     */
    private int maxPoolSize = 20;

    /**
     * 线程池维护线程所允许的空闲时间。默认60秒
     */
    private int keepAliveSeconds = 60;

    /**
     * 队列大小
     */
    private int queueCapacity = 200;

    /**
     * 线程池关闭的时候是否等待所有任务都完成再继续销毁其他的Bean
     */
    private boolean waitForTasksToCompleteOnShutdown = true;

    /**
     * 线程池中任务的等待时间
     */
    private int awaitTerminationSeconds = 60;

    /**
     * 线程的名称前缀
     */
    private String threadNamePrefix = "taskExecutor-";

    /**
     * 拒绝策略
     */
    private RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();
}
