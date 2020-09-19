package cn.waynechu.springcloud.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author zhuwei
 * @since 2018/11/7 12:33
 */
public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;
    private static boolean isMocked;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        if (SpringContextHolder.applicationContext == null) {
            applicationContext = context;
        }
    }

    /**
     * 用于单元测试时覆盖该类
     *
     * @param mockApplicationContext mockContext
     */
    public static synchronized void setApplicationContextForTest(ApplicationContext mockApplicationContext) {
        applicationContext = mockApplicationContext;
        isMocked = true;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 按类类型获取bean
     *
     * @param clazz 类类型
     * @param <T>   t
     * @return bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
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
        return applicationContext.getBean(name, clazz);
    }

    public static void clearHolder() {
        applicationContext = null;
    }

    public static boolean isMocked() {
        return isMocked;
    }
}
