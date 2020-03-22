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
@ConfigurationProperties(prefix = ExecutorProperty.EXECUTOR_CONFIG_PREFIX)
public class ExecutorProperty {
    public static final String EXECUTOR_CONFIG_PREFIX = CommonProperty.COMMON_CONFIG_PREFIX + ".executor";

    /**
     * 线程的名称前缀
     */
    private String threadNamePrefix = "bizThreadPoolExecutor";

    /**
     * 核心线程数。默认为服务器处理器个数
     */
    private Integer corePoolSize = Runtime.getRuntime().availableProcessors();

    /**
     * 最大线程数
     */
    private Integer maxPoolSize = Integer.MAX_VALUE;

    /**
     * 队列大小
     */
    private Integer queueCapacity = Integer.MAX_VALUE;

    /**
     * 线程池维护线程所允许的空闲时间。默认60秒
     * <p>
     * 当任务很多的时候，并且每个任务执行的时间比较短，可以调大时间，即调大线程活动保持时间，可以提高线程的利用率
     */
    private Integer keepAliveSeconds = 60;

    /**
     * 设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
     */
    private Boolean isWaitForTasksToCompleteOnShutdown = false;

    /**
     * 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁
     */
    private Integer awaitTerminationSeconds = 0;

    /**
     * 饱和策略。默认使用终止策略，线程池队列满会抛出RejectedExecutionException异常
     */
    private RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();
}
