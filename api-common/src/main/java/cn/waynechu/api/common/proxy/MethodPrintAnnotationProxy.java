package cn.waynechu.api.common.proxy;

import cn.waynechu.api.common.util.JsonBinder;
import cn.waynechu.api.common.annotation.MethodPrintAnnotation;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author waynechu
 * Created 2018-08-22 23:34
 */
@Slf4j
public class MethodPrintAnnotationProxy implements InvocationHandler {
    private static MethodPrintAnnotationProxy instance = null;

    private MethodPrintAnnotationProxy() {
    }

    public static synchronized MethodPrintAnnotationProxy getInstance() {
        if (instance == null) {
            instance = new MethodPrintAnnotationProxy();
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

        MethodPrintAnnotation printAnnotation = method.getAnnotation(MethodPrintAnnotation.class);

        if (printAnnotation != null) {
            String className;
            if (printAnnotation.isClassFullName()) {
                className = targetObj.getClass().getName();
            } else {
                className = targetObj.getClass().getSimpleName();
            }

            String methodName = method.getName();

            if (printAnnotation.isCallPrompt()) {
                log.info("开始调用类 {} 的 {}() 方法...", className, methodName);
            }

            if (printAnnotation.isParameter()) {
                if (printAnnotation.isParamFormat()) {
                    log.info("类 {} 的 {}() 方法的参数，parameter = {}", className, methodName, JsonBinder.buildNormalBinder().toPrettyJson(args));
                } else {
                    log.info("类 {} 的 {}() 方法的参数，parameter = {}", className, methodName, JsonBinder.buildNormalBinder().toJson(args));
                }
            }

            returnValue = method.invoke(this.targetObj, args);

            if (printAnnotation.isReturnValue()) {
                if (printAnnotation.isParamFormat()) {
                    log.info("类 {} 的 {}() 方法的返回值，return = {}", className, methodName, JsonBinder.buildNormalBinder().toPrettyJson(returnValue));
                } else {
                    log.info("类 {} 的 {}() 方法的返回值，return = {}", className, methodName, JsonBinder.buildNormalBinder().toJson(returnValue));
                }
            }

            if (printAnnotation.isEndPrompt()) {
                log.info("调用类 {} 的 {}() 方法结束。", className, methodName);
            }
        } else {
            returnValue = method.invoke(this.targetObj, args);
        }
        return returnValue;
    }
}
