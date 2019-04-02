package cn.waynechu.springcloud.common.aspect;

import cn.waynechu.springcloud.common.annotation.MethodLogAnnotation;
import cn.waynechu.springcloud.common.util.DequeThreadLocalUtil;
import cn.waynechu.springcloud.common.util.JsonBinder;
import cn.waynechu.springcloud.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 方法调用情况监控切面
 *
 * @author zhuwei
 * @date 2018/11/14 14:16
 */
@Slf4j
public abstract class AbstractMethodLogAspect {

    @Pointcut("@annotation(cn.waynechu.springcloud.common.annotation.MethodLogAnnotation)")
    public void methodPrint() {
        // do nothing here.
    }

    @Before(value = "methodPrint() && @annotation(printAnnotation)")
    public void doBefore(JoinPoint joinPoint, MethodLogAnnotation printAnnotation) {
        if (printAnnotation.isPrintParameter()) {
            String methodName = this.getPrintMethodName(joinPoint, printAnnotation);
            String argsStr = this.getPrintArgsStr(joinPoint, printAnnotation);
            log.info("{}开始调用, 参数: {}", methodName, argsStr);
        }

        // 记录调用开始时间
        if (printAnnotation.isPrintReturn()) {
            DequeThreadLocalUtil.offerFirst(System.currentTimeMillis());
        }
    }

    @After(value = "methodPrint() && @annotation(printAnnotation)")
    public void doAfter(JoinPoint joinPoint, MethodLogAnnotation printAnnotation) {
        // do nothing here.
    }

    @AfterReturning(value = "methodPrint() && @annotation(printAnnotation)", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result, MethodLogAnnotation printAnnotation) {
        if (printAnnotation.isPrintReturn()) {
            String methodName = getPrintMethodName(joinPoint, printAnnotation);
            String returnStr = getPrintReturnStr(result, printAnnotation);
            log.info("{}结束调用, 耗时: {}ms, 返回值: {}", methodName, System.currentTimeMillis() - (long) DequeThreadLocalUtil.pollFirst(), returnStr);
        }
    }

    /**
     * 获取不打印的入参类型，覆写该方法可过滤指定类型的方法参数
     * <ZH_PATTERN>
     * 比如：包含密码等私密数据类型<br>
     * 默认提供 {@code Invisible} 类型
     *
     * @return 不打印的入参类型
     */
    protected static Collection<Class> excludePrintClass() {
        ArrayList<Class> excludePrintClass = new ArrayList<>(1);
        excludePrintClass.add(Invisible.class);
        return excludePrintClass;
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
    private String getPrintMethodName(JoinPoint joinPoint, MethodLogAnnotation printAnnotation) {
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

    private String getPrintArgsStr(JoinPoint joinPoint, MethodLogAnnotation printAnnotation) {
        ArrayList<Object> args = new ArrayList<>();
        for (Object arg : joinPoint.getArgs()) {
            boolean isInstance = false;
            for (Class clazz : excludePrintClass()) {
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

    private String getPrintReturnStr(Object result, MethodLogAnnotation printAnnotation) {
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