package cn.waynechu.webcommon.aspect;

import cn.waynechu.webcommon.annotation.MethodPrintAnnotation;
import cn.waynechu.webcommon.util.JsonBinder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author zhuwei
 * @date 2018/11/14 14:16
 */
@Slf4j
@Aspect
@Component
public class MethodPrintAspect {

    @Pointcut("@annotation(cn.waynechu.webcommon.annotation.MethodPrintAnnotation)")
    public void methodPrint() {
        // do nothing here.
    }

    @Before(value = "methodPrint() && @annotation(printAnnotation)")
    public void doBefore(JoinPoint joinPoint, MethodPrintAnnotation printAnnotation) {
        if (printAnnotation.isPrintParameter()) {
            String methodName = this.getPrintMethodName(joinPoint, printAnnotation);
            String argsStr = this.getPrintArgsStr(joinPoint, printAnnotation);

            log.info("{} 开始调用, 参数: {}", methodName, argsStr);
        }

        // 记录调用开始时间
        if (printAnnotation.isPrintCostTime()) {
            DequeThreadLocalUtil.offerFirst(System.currentTimeMillis());
        }
    }

    @After(value = "methodPrint() && @annotation(printAnnotation)")
    public void doAfter(JoinPoint joinPoint, MethodPrintAnnotation printAnnotation) {
        // do nothing here.
    }

    @AfterReturning(value = "methodPrint() && @annotation(printAnnotation)", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result, MethodPrintAnnotation printAnnotation) {
        String methodName = this.getPrintMethodName(joinPoint, printAnnotation);

        // 打印调用耗时及返回值
        if (printAnnotation.isPrintReturn()) {
            String returnStr = this.getPrintReturnStr(result, printAnnotation);

            log.info("{} 结束调用, 耗时: {}ms, 返回值: {}", methodName, System.currentTimeMillis() - (long) DequeThreadLocalUtil.pollFirst(), returnStr);
        }
    }

    @AfterThrowing(value = "methodPrint() && @annotation(printAnnotation)", throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint, MethodPrintAnnotation printAnnotation, Throwable exception) {
        if (printAnnotation.isPrintException()) {
            String methodName = this.getPrintMethodName(joinPoint, printAnnotation);

            log.error("{} 调用异常: ", methodName, exception);
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
                methodName = joinPoint.getSignature().toString();
            }
            // 或者类简单名
            else {
                methodName = joinPoint.getSignature().toShortString();
            }
        }
        return methodName;
    }

    private String getPrintArgsStr(JoinPoint joinPoint, MethodPrintAnnotation printAnnotation) {
        return toJsonString(this.getArgs(joinPoint), printAnnotation.isFormat());
    }

    private String getPrintReturnStr(Object result, MethodPrintAnnotation printAnnotation) {
        return toJsonString(this.getReturn(result), printAnnotation.isFormat());
    }

    private String toJsonString(Object obj, boolean isFormat) {
        String printStr;
        if (isFormat) {
            printStr = "\n" + JsonBinder.buildAlwaysBinder().toPrettyJson(obj);
        } else {
            printStr = JsonBinder.buildAlwaysBinder().toJson(obj);
        }
        return printStr;
    }

    /**
     * 获得切点处指定类型的注解
     *
     * @param joinPoint       切点
     * @param annotationClass 注解类型
     * @return 注解
     */
    public <T extends Annotation> T getAnnotation(JoinPoint joinPoint, Class<T> annotationClass) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(annotationClass);
        }
        return null;
    }

    /**
     * 获得切点处注解列表
     *
     * @param joinPoint 切点
     * @return 注解列表
     */
    public Annotation[] getDeclearAnnotation(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getDeclaredAnnotations();
        }
        return new Annotation[0];
    }
}