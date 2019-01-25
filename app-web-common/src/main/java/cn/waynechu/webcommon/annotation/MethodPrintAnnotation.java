package cn.waynechu.webcommon.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author zhuwei
 * @date 2018/08/22 23:25
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
public @interface MethodPrintAnnotation {

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

    /**
     * 打印方法调用时间
     *
     * @return default true
     */
    boolean isPrintCostTime() default true;

    /**
     * 打印方法抛出的异常
     *
     * @return default true
     */
    boolean isPrintException() default true;
}