package cn.waynechu.springcloud.test.agent.bytebuddy;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author zhuwei
 * @since 2021/1/28 13:49
 */
@Slf4j
public class TimeInterceptor {

    @RuntimeType
    public static Object intercept(@Origin Method method,
                                   @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        try {
            // 原有函数执行
            return callable.call();
        } finally {
            log.info(method + ": took " + (System.currentTimeMillis() - start) + "ms");
        }
    }
}
