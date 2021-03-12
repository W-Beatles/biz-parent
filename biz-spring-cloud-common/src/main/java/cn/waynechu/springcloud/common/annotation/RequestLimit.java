package cn.waynechu.springcloud.common.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于IP访问控制
 * 可添加黑名单、白名单策略
 *
 * @author zhuwei
 * @since 2018/11/6 16:02
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RequestLimit {

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
