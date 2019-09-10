package cn.waynechu.springcloud.common.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 *
 * @author zhuwei
 * @date 2019/9/10 19:43
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
public @interface DistributedLock {

    String name();

    String key();

    int expire() default Integer.MIN_VALUE;

    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
