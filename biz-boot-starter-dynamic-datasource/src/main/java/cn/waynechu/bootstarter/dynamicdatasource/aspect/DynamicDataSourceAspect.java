package cn.waynechu.bootstarter.dynamicdatasource.aspect;

import cn.waynechu.bootstarter.dynamicdatasource.annotion.SwitchDataSource;
import cn.waynechu.bootstarter.dynamicdatasource.toolkit.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author zhuwei
 * @date 2019/12/27 13:35
 */
@Slf4j
//@Aspect
public class DynamicDataSourceAspect {

    @Before("@within(switchDataSource) || @annotation(switchDataSource)")
    public void changeDataSource(JoinPoint point, SwitchDataSource switchDataSource) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        SwitchDataSource annotation = method.getAnnotation(SwitchDataSource.class);

        if (annotation == null) {
            annotation = point.getTarget().getClass().getAnnotation(SwitchDataSource.class);
            if (annotation == null) {
                return;
            }
        }

        String value = annotation.value();
        DynamicDataSourceContextHolder.push(value);
    }

    @After("@within(switchDataSource) || @annotation(switchDataSource)")
    public void clean(SwitchDataSource switchDataSource) {
        // 清理数据源的标签
        DynamicDataSourceContextHolder.peek();
    }
}
