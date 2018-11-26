package cn.waynechu.common.aspect;

import cn.waynechu.common.annotation.MethodPrintAnnotation;
import cn.waynechu.common.util.JsonBinder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @author zhuwei
 * @date 2018/11/14 14:16
 */
public abstract class AbstractMethodPrintAspect {
    private static final Logger log = LoggerFactory.getLogger(AbstractMethodPrintAspect.class);

    private ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public abstract void methodPrint();

    @Before(value = "methodPrint() && @annotation(printAnnotation)")
    public void doBefore(JoinPoint joinPoint, MethodPrintAnnotation printAnnotation) {
        if (log.isDebugEnabled()) {
            if (printAnnotation.isPrintParameter()) {
                String methodName;
                String argsStr;
                if (!StringUtils.isEmpty(printAnnotation.description())) {
                    methodName = joinPoint.getSignature().getName();
                } else {
                    if (printAnnotation.isClassFullName()) {
                        methodName = joinPoint.getSignature().toLongString();
                    } else {
                        methodName = joinPoint.getSignature().toShortString();
                    }
                }

                if (printAnnotation.isParamFormat()) {
                    argsStr = JsonBinder.buildNormalBinder().toPrettyJson(getArgs(joinPoint));
                } else {
                    argsStr = JsonBinder.buildNormalBinder().toJson(getArgs(joinPoint));
                }
                log.info("开始调用 {}, 参数: {}", methodName, argsStr);
            }

            if (printAnnotation.isCostTime()) {
                threadLocal.set(System.currentTimeMillis());
            }
        }
    }

    @After(value = "methodPrint() && @annotation(printAnnotation)")
    public void doAfter(JoinPoint joinPoint, MethodPrintAnnotation printAnnotation) {
        // do nothing here.
    }

    @AfterReturning(returning = "result", value = "methodPrint() && @annotation(printAnnotation)")
    public void doAfterReturning(JoinPoint joinPoint, Object result, MethodPrintAnnotation printAnnotation) {
        if (log.isDebugEnabled()) {
            if (printAnnotation.isReturnValue()) {
                if (printAnnotation.isParamFormat()) {
                    log.info("结束调用 {}, 返回值: {}", joinPoint.getSignature(), JsonBinder.buildNormalBinder().toPrettyJson(getReturn(joinPoint)));
                } else {
                    log.info("结束调用 {}, 返回值: {}", joinPoint.getSignature(), JsonBinder.buildNormalBinder().toJson(getReturn(joinPoint)));
                }
            }

            if (printAnnotation.isCostTime()) {
                log.debug("调用耗时: {} 毫秒", System.currentTimeMillis() - threadLocal.get());
                threadLocal.remove();
            }
        }
    }

    @AfterThrowing(value = "methodPrint() && @annotation(printAnnotation)", throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint, MethodPrintAnnotation printAnnotation, Throwable exception) {
        if (log.isDebugEnabled() && printAnnotation.isException()) {
            log.error("{} 调用异常: {}", joinPoint.getSignature(), exception);
        }
    }

    /**
     * 获取方法参数
     *
     * @param joinPoint 切点
     * @return 方法参数
     */
    protected abstract Object getArgs(JoinPoint joinPoint);

    /**
     * 获取方法返回值
     *
     * @param result 返回值
     * @return result
     */
    protected abstract Object getReturn(Object result);
}