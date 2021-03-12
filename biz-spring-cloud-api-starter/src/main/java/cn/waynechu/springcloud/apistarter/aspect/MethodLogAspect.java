package cn.waynechu.springcloud.apistarter.aspect;

import cn.waynechu.springcloud.common.annotation.MethodLog;
import cn.waynechu.springcloud.common.util.DequeThreadLocal;
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

/**
 * 方法调用情况监控切面默认实现
 *
 * <pre>
 * 切面生效条件:
 * 1.方法上添加{@code cn.waynechu.springcloud.common.annotation.MethodLogAnnotation} 注解
 * </pre>
 *
 * @author zhuwei
 * @since 2019/3/29 17:10
 */
@Slf4j
@Aspect
@ConditionalOnMissingBean(name = "methodLogAspect")
public class MethodLogAspect {

    private static final DequeThreadLocal<Long> DEQUE_THREAD_LOCAL = new DequeThreadLocal<>();

    @Before("@annotation(printAnnotation)")
    public void doBefore(JoinPoint joinPoint, MethodLog printAnnotation) {
        if (printAnnotation.isPrintParameter()) {
            String methodName = printAnnotation.value();
            log.info("{}调用开始, 参数: {}", methodName, joinPoint.getArgs());
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
            log.info("{}调用结束, 耗时: {}ms, 返回值: {}", methodName, System.currentTimeMillis() - DEQUE_THREAD_LOCAL.pollFirst(), result);
        }
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