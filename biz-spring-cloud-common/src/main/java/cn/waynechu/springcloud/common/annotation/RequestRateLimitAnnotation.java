package cn.waynechu.springcloud.common.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * 用于IP访问控制
 * 可添加黑名单、白名单策略
 *
 * @author zhuwei
 * @date 2018/11/6 16:02
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RequestRateLimitAnnotation {

    /**
     * 限制某时间段内可访问次数，默认5次
     */
    int limitCounts() default 5;

    /**
     * 限制访问的间段，默认为60秒
     */
    int timeSecond() default 60;

    /**
     * IP白名单，跳过访问次数检查
     */
    String[] whiteList() default {"localhost"};

    /**
     * IP黑名单，无接口访问权限
     */
    String[] blackList() default {};
}
