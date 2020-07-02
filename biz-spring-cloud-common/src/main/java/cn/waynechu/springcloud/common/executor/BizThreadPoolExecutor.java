package cn.waynechu.springcloud.common.executor;

import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;

/**
 * 自定义线程池
 * <p>
 * 拷贝MDC信息，防止由于异步线程造成的日志信息丢失问题
 *
 * @author zhuwei
 * @date 2019/10/18 16:49
 */
public class BizThreadPoolExecutor extends ThreadPoolTaskExecutor {

    /**
     * 所有线程都会委托给这个execute方法，在这个方法中我们把父线程的MDC内容赋值给子线程
     * https://logback.qos.ch/manual/mdc.html#managedThreads
     *
     * @param runnable runnable
     */
    @Override
    public void execute(Runnable runnable) {
        // 获取父线程MDC信息
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        // 获取父线程request属性
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        super.execute(() -> run(runnable, contextMap, requestAttributes));
    }

    /**
     * 子线程委托的执行方法
     *
     * @param runnable          {@link Runnable}
     * @param contextMap        父线程MDC内容
     * @param requestAttributes ra
     */
    private void run(Runnable runnable, Map<String, String> contextMap, RequestAttributes requestAttributes) {
        // 传MDC信息到子线程
        if (contextMap != null) {
            MDC.setContextMap(contextMap);
        }
        // 传request属性到子线程
        if (requestAttributes != null) {
            RequestContextHolder.setRequestAttributes(requestAttributes);
        }

        try {
            // 执行异步操作
            runnable.run();
        } finally {
            // 清空当前子线程MDC内容
            MDC.clear();
            // 重置当前子线程request属性
            RequestContextHolder.resetRequestAttributes();
        }
    }
}
