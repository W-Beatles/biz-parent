package cn.waynechu.springcloud.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author zhuwei
 * @since 2018/11/7 12:33
 */
public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext APPLICATION_CONTEXT = null;
    private static boolean IS_MOCKED;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        if (SpringContextHolder.APPLICATION_CONTEXT == null) {
            APPLICATION_CONTEXT = context;
        }
    }

    /**
     * 用于单元测试时覆盖该类
     *
     * @param mockApplicationContext mockContext
     */
    public static synchronized void setApplicationContextForTest(ApplicationContext mockApplicationContext) {
        APPLICATION_CONTEXT = mockApplicationContext;
        IS_MOCKED = true;
    }

    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    public static Object getBean(String name) {
        return APPLICATION_CONTEXT.getBean(name);
    }

    /**
     * 按类类型获取bean
     *
     * @param clazz 类类型
     * @param <T>   t
     * @return bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return APPLICATION_CONTEXT.getBean(clazz);
    }

    /**
     * 按实例名获取bean
     *
     * @param name  实例名
     * @param clazz 类类型
     * @param <T>   t
     * @return bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return APPLICATION_CONTEXT.getBean(name, clazz);
    }

    public static void clearHolder() {
        APPLICATION_CONTEXT = null;
    }

    public static boolean isMocked() {
        return IS_MOCKED;
    }
}
