package cn.waynechu.api.common.annotation;

import java.lang.annotation.*;

/**
 * @author zhuwei
 * @date 2018/08/22 23:25
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface MethodPrintAnnotation {

    String value() default "";

    String description() default "";

    /**
     * 打印调用开始
     *
     * @return default true
     */
    boolean isCallPrompt() default true;

    /**
     * 打印参数
     *
     * @return default true
     */
    boolean isParameter() default true;

    /**
     * 打印返回值
     *
     * @return default true
     */
    boolean isReturnValue() default true;

    /**
     * 打印调用结束
     *
     * @return default true
     */
    boolean isEndPrompt() default true;

    /**
     * 参数格式化打印
     *
     * @return default false
     */
    boolean isParamFormat() default false;

    /**
     * 打印全称类名
     *
     * @return default false
     */
    boolean isClassFullName() default false;
}