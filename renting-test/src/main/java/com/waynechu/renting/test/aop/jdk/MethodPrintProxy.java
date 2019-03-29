package com.waynechu.renting.test.aop.jdk;

import cn.waynechu.webcommon.annotation.MethodLogAnnotation;
import cn.waynechu.webcommon.util.JsonBinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author waynechu
 * Created 2018-08-22 23:34
 */
@Slf4j
public class MethodPrintProxy implements InvocationHandler {
    private static MethodPrintProxy instance = null;

    private MethodPrintProxy() {
    }

    public static synchronized MethodPrintProxy getInstance() {
        if (instance == null) {
            instance = new MethodPrintProxy();
        }
        return instance;
    }

    private Object targetObj;

    public Object bind(Object o) {
        this.targetObj = o;
        return Proxy.newProxyInstance(targetObj.getClass().getClassLoader(), o.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object returnValue;

        MethodLogAnnotation printAnnotation = method.getAnnotation(MethodLogAnnotation.class);

        if (printAnnotation != null) {
            String methodName = this.getPrintMethodName(printAnnotation, targetObj);
            String argsStr = this.getJsonStr(args, printAnnotation.isFormat());
            log.info("[MethodPrintProxy] {} 开始调用, 参数: {}", methodName, argsStr);

            returnValue = method.invoke(this.targetObj, args);

            String returnStr = this.getJsonStr(returnValue, printAnnotation.isFormat());
            log.debug("[MethodPrintProxy] {} 结束调用，返回值: {}", methodName, returnStr);
        } else {
            returnValue = method.invoke(this.targetObj, args);
        }
        return returnValue;
    }

    private String getPrintMethodName(MethodLogAnnotation printAnnotation, Object targetObj) {
        String methodName;
        // 默认打印方法描述字段
        if (!StringUtils.isEmpty(printAnnotation.value())) {
            methodName = printAnnotation.value();
        } else if (!StringUtils.isEmpty(printAnnotation.description())) {
            methodName = printAnnotation.description();
        } else {
            // 否则打印类全名
            if (printAnnotation.isClassFullName()) {
                methodName = targetObj.getClass().getName();
            }
            // 或者类简单名
            else {
                methodName = targetObj.getClass().getSimpleName();
            }
        }
        return methodName;
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
