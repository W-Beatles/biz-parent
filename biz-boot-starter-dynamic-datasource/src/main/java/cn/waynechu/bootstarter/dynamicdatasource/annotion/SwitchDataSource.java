package cn.waynechu.bootstarter.dynamicdatasource.annotion;

import java.lang.annotation.*;

/**
 * @author zhuwei
 * @date 2019/12/27 13:33
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SwitchDataSource {

    String value();
}
