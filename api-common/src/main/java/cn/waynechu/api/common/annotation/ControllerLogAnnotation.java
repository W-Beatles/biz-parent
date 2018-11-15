package cn.waynechu.api.common.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author zhuwei
 * @date 2018/11/14 14:33
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD})
public @interface ControllerLogAnnotation {

    String description() default "";

    boolean isPrintRequestData() default true;

    boolean isPrintResultData() default true;

    boolean isPrintThrowing() default true;

    boolean isPrintSpendTime() default true;

}
