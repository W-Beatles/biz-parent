package cn.waynechu.common.aspect;

import cn.waynechu.common.annotation.ControllerLogAspectAnnotation;
import cn.waynechu.common.util.JsonBinder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhuwei
 * @date 2018/11/14 14:16
 */
public abstract class AbstractControllerLogAspect {
    private static final Logger log = LoggerFactory.getLogger(AbstractControllerLogAspect.class);

    private ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public abstract void controllerLog();

    @Before(value = "controllerLog()&& @annotation(logAnnotation)")
    public void doBefore(JoinPoint joinPoint, ControllerLogAspectAnnotation logAnnotation) {
        if (log.isInfoEnabled()) {
            if (logAnnotation.isPrintRequestData()) {
                log.info("{}接口调用开始：requestData = {}", logAnnotation.description(), JsonBinder.buildNonDefaultBinder().toJson(getRequestData(joinPoint)));
            }

            if (logAnnotation.isPrintSpendTime()) {
                threadLocal.set(System.currentTimeMillis());
            }
        }
    }

    @After(value = "controllerLog()&& @annotation(logAnnotation)")
    public void doAfter(JoinPoint joinPoint, ControllerLogAspectAnnotation logAnnotation) {
        // do nothing here.
    }

    @AfterReturning(returning = "result", value = "controllerLog()&& @annotation(logAnnotation)")
    public void doAfterReturning(JoinPoint joinPoint, Object result, ControllerLogAspectAnnotation logAnnotation) {
        if (log.isInfoEnabled() && logAnnotation.isPrintResultData()) {
            log.info("{}接口结束调用：resultData = {}", logAnnotation.description(), JsonBinder.buildNonDefaultBinder().toJson(getResultData(result)));
        }

        if (log.isInfoEnabled() && logAnnotation.isPrintSpendTime()) {
            log.info("{}接口调用耗时：{}毫秒", logAnnotation.description(), System.currentTimeMillis() - threadLocal.get());
            threadLocal.remove();
        }
    }

    @AfterThrowing(value = "controllerLog()&& @annotation(logAnnotation)", throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint, ControllerLogAspectAnnotation logAnnotation, Throwable exception) {
        if (log.isInfoEnabled() && logAnnotation.isPrintThrowing()) {
            log.error("{}接口操作异常：requestData={}", logAnnotation.description(), JsonBinder.buildNonDefaultBinder().toJson(getRequestData(joinPoint)), exception);
        }
    }

    protected abstract Object getRequestData(JoinPoint joinPoint);

    protected abstract Object getResultData(Object result);
}