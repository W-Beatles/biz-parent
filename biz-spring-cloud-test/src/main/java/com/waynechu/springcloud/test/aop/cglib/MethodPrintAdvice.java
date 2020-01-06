package com.waynechu.springcloud.test.aop.cglib;

import cn.waynechu.springcloud.common.annotation.MethodLog;
import cn.waynechu.springcloud.common.util.JsonBinder;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * @author zhuwei
 * @date 2019/1/14 20:42
 */
@Slf4j
public class MethodPrintAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object returnValue;

        // 获取目标类
        Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
        // 获取指定方法
        Method specificMethod = ClassUtils.getMostSpecificMethod(invocation.getMethod(), targetClass);
        // 获取真正执行的方法,可能存在桥接方法
        final Method userDeclaredMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);

        // 获取方法上注解
        MethodLog printAnnotation = AnnotatedElementUtils.findMergedAnnotation(userDeclaredMethod, MethodLog.class);
        // 获取返回类型
        if (printAnnotation != null) {
            String methodName = invocation.getMethod().getName();
            Object[] arguments = invocation.getArguments();
            log.info("[MethodPrintAdvice] {} 调用开始, 参数: {}", methodName, arguments);
            returnValue = invocation.proceed();

            String returnStr = this.getJsonStr(returnValue, printAnnotation.isFormat());
            log.debug("[MethodPrintAdvice] {} 调用结束，返回值: {}", methodName, returnStr);
        } else {
            returnValue = invocation.proceed();
        }
        return returnValue;
    }

    private String getJsonStr(Object args, boolean isFormat) {
        String printStr;
        if (isFormat) {
            printStr = "\n" + JsonBinder.buildAlwaysBinder().toPrettyJson(args);
        } else {
            printStr = JsonBinder.buildAlwaysBinder().toJson(args);
        }
        return printStr;
    }
}
