package cn.waynechu.springcloud.apistarter.executor;

import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;

/**
 * @author zhuwei
 * @date 2019/10/18 16:49
 */
public class BizThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    /**
     * 所有线程都会委托给这个execute方法，在这个方法中我们把父线程的MDC内容赋值给子线程
     * https://logback.qos.ch/manual/mdc.html#managedThreads
     *
     * @param runnable runnable
     */
    @Override
    public void execute(Runnable runnable) {
        // 获取父线程MDC中的内容，必须在run方法之前，否则等异步线程执行的时候有可能MDC里面的值已经被清空了，这个时候就会返回null
        Map<String, String> context = MDC.getCopyOfContextMap();
        super.execute(() -> run(runnable, context));
    }

    /**
     * 子线程委托的执行方法
     *
     * @param runnable {@link Runnable}
     * @param context  父线程MDC内容
     */
    private void run(Runnable runnable, Map<String, String> context) {
        // 将父线程的MDC内容传给子线程
        if (context != null) {
            MDC.setContextMap(context);
        }

        try {
            // 执行异步操作
            runnable.run();
        } finally {
            // 清空MDC内容
            MDC.clear();
        }
    }
}
