package cn.waynechu.springcloud.common.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 * <p>
 * 用于做方法访问控制
 *
 * @author zhuwei
 * @since 2019/9/10 19:43
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
public @interface DistributedLock {

    /**
     * 锁名称。默认以 类全限定名.方法名 作为名称
     *
     * @return 锁名称
     */
    String name() default "";

    /**
     * 锁key值。锁的唯一标识
     *
     * @return 锁key值
     */
    String key();

    /**
     * 锁超时时间。默认5秒
     *
     * @return 锁超时时间
     */
    int expire() default 5;

    /**
     * 时间单位。默认秒
     *
     * @return 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 方法返回自动释放锁。默认是
     *
     * <pre>
     *     当该值为否时，分布式锁退化成访问控制作用，限定时间内只有一个请求能够持有该锁，直到锁自动过期
     * </pre>
     *
     * @return 自动释放锁
     */
    boolean autoRelease() default true;
}
