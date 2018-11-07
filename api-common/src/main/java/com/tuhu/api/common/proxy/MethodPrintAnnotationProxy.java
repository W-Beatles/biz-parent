package cn.waynechu.method.print;

import com.alibaba.fastjson.JSONObject;
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
        String className = targetObj.getClass().getSimpleName();
        String methodName = method.getName();

        if (printAnnotation != null && printAnnotation.isCallPrompt() && log.isInfoEnabled()) {

            log.info("调用类 " + className + " 的方法 " + methodName + " 成功！");
        }

        if (printAnnotation != null && printAnnotation.isParameter() && log.isInfoEnabled()) {
            log.info("类 " + className + " 的方法 " + methodName + " 的参数信息，parameter={}", JSONObject.toJSONString(args));
        }

        returnValue = method.invoke(this.targetObj, args);

        if (printAnnotation != null && printAnnotation.isReturnValue() && log.isInfoEnabled()) {
            log.info("类 " + className + " 的方法 " + methodName + " 的返回值，return={}", returnValue);
        }

        if (printAnnotation != null && printAnnotation.isEndPrompt() && log.isInfoEnabled()) {
            log.info("类 " + className + " 的方法 " + methodName + " 结束调用");
        }

        return returnValue;
    }
}
