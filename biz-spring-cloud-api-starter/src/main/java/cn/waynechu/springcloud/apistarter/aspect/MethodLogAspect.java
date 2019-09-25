package cn.waynechu.springcloud.apistarter.aspect;

import cn.waynechu.springcloud.common.annotation.MethodLog;
import cn.waynechu.springcloud.common.aspect.Invisible;
import cn.waynechu.springcloud.common.util.DequeThreadLocal;
import cn.waynechu.springcloud.common.util.JsonBinder;
import cn.waynechu.springcloud.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 方法调用情况监控切面默认实现
 *
 * <pre>
 * 切面生效条件:
 * 1.方法上添加{@code cn.waynechu.springcloud.common.annotation.MethodLogAnnotation} 注解
 * </pre>
 *
 * @author zhuwei
 * @date 2019/3/29 17:10
 */
@Slf4j
@Aspect
@ConditionalOnMissingBean(name = "methodLogAspect")
public class MethodLogAspect {

    private static final DequeThreadLocal<Long> DEQUE_THREAD_LOCAL = new DequeThreadLocal<>();

    @Before("@annotation(printAnnotation)")
    public void doBefore(JoinPoint joinPoint, MethodLog printAnnotation) {
        if (printAnnotation.isPrintParameter()) {
            String methodName = this.getPrintMethodName(joinPoint, printAnnotation);
            String argsStr = this.getPrintArgsJsonStr(joinPoint, printAnnotation);
            log.info("{}开始调用, 参数: {}", methodName, argsStr);
        }

        // 记录调用开始时间
        if (printAnnotation.isPrintReturn()) {
            DEQUE_THREAD_LOCAL.offerFirst(System.currentTimeMillis());
        }
    }

    @AfterReturning(value = "@annotation(printAnnotation)", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result, MethodLog printAnnotation) {
        if (printAnnotation.isPrintReturn()) {
            String methodName = getPrintMethodName(joinPoint, printAnnotation);
            String returnStr = getPrintReturnStr(result, printAnnotation);
            log.info("{}结束调用, 耗时: {}ms, 返回值: {}", methodName, System.currentTimeMillis() - DEQUE_THREAD_LOCAL.pollFirst(), returnStr);
        }
    }

    /**
     * 获取不打印的入参类型，覆写该方法可过滤指定类型的方法参数
     * <p>
     * 比如：包含密码等私密数据类型<br>
     * 默认提供 {@code Invisible} 类型
     *
     * @return 不打印的入参类型
     */
    private static Collection<Class> excludePrintClass() {
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
    private Object getReturn(Object result) {
        return result;
    }

    /**
     * 获取需要打印的方法名
     *
     * @param joinPoint       切点
     * @param printAnnotation 注解
     * @return 方法名
     */
    private String getPrintMethodName(JoinPoint joinPoint, MethodLog printAnnotation) {
        // 默认打印自定义的方法描述字段
        if (StringUtil.isNotEmpty(printAnnotation.value())) {
            return printAnnotation.value();
        } else if (StringUtil.isNotEmpty(printAnnotation.description())) {
            return printAnnotation.description();
        } else {
            // 否则打印类全名
            if (printAnnotation.isClassFullName()) {
                return joinPoint.getSignature().toString();
            }
            // 或者类简单名
            else {
                return joinPoint.getSignature().toShortString();
            }
        }
    }

    private String getPrintArgsJsonStr(JoinPoint joinPoint, MethodLog printAnnotation) {
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

    private String getPrintReturnStr(Object result, MethodLog printAnnotation) {
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
    public Annotation[] getDeclareAnnotation(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getDeclaredAnnotations();
        }
        return new Annotation[0];
    }
}