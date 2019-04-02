package cn.waynechu.springcloud.common.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 方法调用情况监控注解
 *
 * @author zhuwei
 * @date 2018/08/22 23:25
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
public @interface MethodLogAnnotation {

    @AliasFor("description")
    String value() default "";

    /**
     * 方法描述
     *
     * @return default ""
     */
    @AliasFor("value")
    String description() default "";

    /**
     * 打印参数
     *
     * @return default true
     */
    boolean isPrintParameter() default true;

    /**
     * Json格式化打印
     *
     * @return default false
     */
    boolean isFormat() default false;

    /**
     * 打印返回值
     *
     * @return default true
     */
    boolean isPrintReturn() default true;

    /**
     * 打印全称类名
     *
     * @return default false
     */
    boolean isClassFullName() default false;
}