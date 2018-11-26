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
                String methodName = this.getPrintMethodName(joinPoint, printAnnotation);
                String argsStr = this.getPrintArgsStr(joinPoint, printAnnotation);

                log.info("开始调用: {}, 参数: {}", methodName, argsStr);
            }

            // 记录调用开始时间
            if (printAnnotation.isPrintCostTime()) {
                threadLocal.set(System.currentTimeMillis());
            }
        }
    }

    @After(value = "methodPrint() && @annotation(printAnnotation)")
    public void doAfter(JoinPoint joinPoint, MethodPrintAnnotation printAnnotation) {
        // do nothing here.
    }

    @AfterReturning(value = "methodPrint() && @annotation(printAnnotation)", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result, MethodPrintAnnotation printAnnotation) {
        if (log.isDebugEnabled()) {
            if (printAnnotation.isPrintReturn()) {
                String methodName = this.getPrintMethodName(joinPoint, printAnnotation);
                String argsStr = this.getPrintReturnStr(result, printAnnotation);

                log.debug("结束调用: {}, 返回值: {}", methodName, argsStr);
            }

            // 打印调用耗时
            if (printAnnotation.isPrintCostTime()) {
                log.debug("调用耗时: {} 毫秒", System.currentTimeMillis() - threadLocal.get());
                threadLocal.remove();
            }
        }
    }

    @AfterThrowing(value = "methodPrint() && @annotation(printAnnotation)", throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint, MethodPrintAnnotation printAnnotation, Throwable exception) {
        if (log.isDebugEnabled() && printAnnotation.isPrintException()) {
            log.error("{} 调用异常: {}", joinPoint.getSignature(), exception);
        }
    }

    /**
     * 获取方法参数，覆写该方法可过滤指定类型的方法参数
     *
     * @param joinPoint 切点
     * @return 方法参数
     */
    protected Object getArgs(JoinPoint joinPoint) {
        return joinPoint.getArgs();
    }

    /**
     * 获取方法返回值，覆写该方法可过滤指定类型的方法参数
     *
     * @param result 返回值
     * @return result
     */
    protected Object getReturn(Object result) {
        return result;
    }

    /**
     * 获取需要打印的方法名
     *
     * @param joinPoint       切点
     * @param printAnnotation 注解
     * @return 方法名
     */
    private String getPrintMethodName(JoinPoint joinPoint, MethodPrintAnnotation printAnnotation) {
        String methodName;
        // 默认打印方法描述字段
        if (!StringUtils.isEmpty(printAnnotation.value())) {
            methodName = printAnnotation.value();
        } else if (!StringUtils.isEmpty(printAnnotation.description())) {
            methodName = printAnnotation.description();
        } else {
            // 否则打印类全名
            if (printAnnotation.isClassFullName()) {
                methodName = joinPoint.getSignature().toLongString();
            }
            // 或者类简单名
            else {
                methodName = joinPoint.getSignature().toShortString();
            }
        }
        return methodName;
    }

    private String getPrintArgsStr(JoinPoint joinPoint, MethodPrintAnnotation printAnnotation) {
        return generatePrintStr(this.getArgs(joinPoint), printAnnotation.isFormat());
    }

    private String getPrintReturnStr(Object result, MethodPrintAnnotation printAnnotation) {
        return generatePrintStr(this.getReturn(result), printAnnotation.isFormat());
    }

    private String generatePrintStr(Object obj, boolean isFormat) {
        String printStr;
        if (isFormat) {
            printStr = JsonBinder.buildNormalBinder().toPrettyJson(obj);
        } else {
            printStr = JsonBinder.buildNormalBinder().toJson(obj);
        }
        return printStr;
    }
}