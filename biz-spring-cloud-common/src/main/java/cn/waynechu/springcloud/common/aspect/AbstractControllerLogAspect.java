package cn.waynechu.springcloud.common.aspect;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Controller层方法调用情况切面
 *
 * <pre>
 * 加入注解@ApiOperation将打印接口请求、返回及异常日志
 *
 * 该切面还会添加如下信息到MDC上下文中:
 *   url: 接口访问路径
 *   timeTaken: 接口访问耗时
 * </pre>
 *
 * @author zhuwei
 * @since 2019/03/22 14:16
 */
@Slf4j
public abstract class AbstractControllerLogAspect {

    public static final String MDC_KEY_URL = "url";
    public static final String MDC_KEY_TIME_TAKEN = "timeTaken";

    private ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public void controllerLog() {
        // do nothing here.
    }

    @Before(value = "controllerLog() && @annotation(logAnnotation)")
    public void doBefore(JoinPoint joinPoint, ApiOperation logAnnotation) {
        log.info("{}调用开始, 参数: {}", logAnnotation.value(), joinPoint.getArgs());
        // 记录调用开始的时间
        threadLocal.set(System.currentTimeMillis());

        // 添加MDC记录  url: 请求地址
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            MDC.put(MDC_KEY_URL, ((ServletRequestAttributes) attributes).getRequest().getRequestURI());
        }
    }

    @After(value = "controllerLog() && @annotation(logAnnotation)")
    public void doAfter(JoinPoint joinPoint, ApiOperation logAnnotation) {
        // do nothing here.
    }

    @AfterReturning(value = "controllerLog() && @annotation(logAnnotation)", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, ApiOperation logAnnotation, Object result) {
        Long timeTaken = System.currentTimeMillis() - threadLocal.get();
        // 添加MDC记录  timeTaken: 调用耗时
        MDC.put(MDC_KEY_TIME_TAKEN, String.valueOf(timeTaken));

        log.info("{}调用结束, 耗时: {}ms, 返回值: {}", logAnnotation.value(), timeTaken, result);
        // clear
        threadLocal.remove();
        MDC.clear();
    }
}