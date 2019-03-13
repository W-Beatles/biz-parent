package cn.waynechu.webcommon.aspect;

import cn.waynechu.webcommon.annotation.MethodPrintAnnotation;
import cn.waynechu.webcommon.util.DequeThreadLocalUtil;
import cn.waynechu.webcommon.util.JsonBinder;
import cn.waynechu.webcommon.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author zhuwei
 * @date 2018/11/14 14:16
 */
@Slf4j
public abstract class BaseMethodPrintAspect {

    @Pointcut("@annotation(cn.waynechu.webcommon.annotation.MethodPrintAnnotation)")
    public void methodPrint() {
        // do nothing here.
    }

    @Before(value = "methodPrint() && @annotation(printAnnotation)")
    public void doBefore(JoinPoint joinPoint, MethodPrintAnnotation printAnnotation) {
        if (printAnnotation.isPrintParameter()) {
            String methodName = this.getPrintMethodName(joinPoint, printAnnotation);
            String argsStr = this.getPrintArgsStr(joinPoint, printAnnotation);
            log.info("{}开始调用, 参数: {}", methodName, argsStr);
        }

        // 记录调用开始时间
        if (printAnnotation.isPrintReturn() && printAnnotation.isPrintCostTime()) {
            DequeThreadLocalUtil.offerFirst(System.currentTimeMillis());
        }
    }

    @After(value = "methodPrint() && @annotation(printAnnotation)")
    public void doAfter(JoinPoint joinPoint, MethodPrintAnnotation printAnnotation) {
        // do nothing here.
    }

    @AfterReturning(value = "methodPrint() && @annotation(printAnnotation)", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result, MethodPrintAnnotation printAnnotation) {
        if (printAnnotation.isPrintReturn()) {
            String methodName = this.getPrintMethodName(joinPoint, printAnnotation);
            String returnStr = this.getPrintReturnStr(result, printAnnotation);
            log.info("{}结束调用, 耗时: timeToken={}ms, 返回值: {}", methodName, System.currentTimeMillis() - (long) DequeThreadLocalUtil.pollFirst(), returnStr);
        }
    }

    @AfterThrowing(value = "methodPrint() && @annotation(printAnnotation)", throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint, MethodPrintAnnotation printAnnotation, Throwable exception) {
        if (printAnnotation.isPrintException()) {
            String methodName = this.getPrintMethodName(joinPoint, printAnnotation);
            log.error("{}调用异常: ", methodName, exception);
        }
    }

    /**
     * 获取不打印的入参类型，覆写该方法可过滤指定类型的方法参数
     * 比如 HttpServletResponse、MultipartFile 或者 包含密码等私密数据 的对象等
     *
     * @return 不打印的入参类型
     */
    protected Collection<Class> excludePrintClass() {
        return Collections.emptyList();
    }

    /**
     * 获取方法返回值。覆写该方法可过滤指定类型的方法返回值
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
        // 默认打印自定义的方法描述字段
        if (StringUtil.isNotEmpty(printAnnotation.value())) {
            methodName = printAnnotation.value();
        } else if (StringUtil.isNotEmpty(printAnnotation.description())) {
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
        ArrayList<Object> args = new ArrayList<>();
        for (Object arg : joinPoint.getArgs()) {
            boolean isInstance = false;
            for (Class clazz : this.excludePrintClass()) {
                if (clazz.isInstance(arg)) {
                    isInstance = true;
                    break;
                }
            }

            if (!isInstance) {
                args.add(arg);
            }
        }
        return toJsonString(args, printAnnotation.isFormat());
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