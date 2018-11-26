package cn.waynechu.common.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author zhuwei
 * @date 2018/08/22 23:25
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface MethodPrintAnnotation {

    @AliasFor("description")
    String value() default "";

    /**
     * 方法描述
     *
     * @return default ""
     */
    String description() default "";

    /**
     * 打印参数
     *
     * @return default true
     */
    boolean isPrintParameter() default true;

    /**
     * 参数格式化打印
     *
     * @return default false
     */
    boolean isParamFormat() default false;

    /**
     * 打印返回值
     *
     * @return default true
     */
    boolean isReturnValue() default true;

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
    boolean isCostTime() default true;

    /**
     * 打印方法抛出的异常
     *
     * @return default true
     */
    boolean isException() default true;
}