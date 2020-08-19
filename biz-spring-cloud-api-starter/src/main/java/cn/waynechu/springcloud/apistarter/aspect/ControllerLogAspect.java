package cn.waynechu.springcloud.apistarter.aspect;

import cn.waynechu.springcloud.common.aspect.AbstractControllerLogAspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Controller层日志切面默认实现
 *
 * <pre>
 * 切面生效条件:
 * 1. 该类位于controller包下
 * 2. 方法上添加了{@code io.swagger.annotations.ApiOperation} 注解
 *
 * 注: 如想替换默认的切点controller包，可以继承AbstractControllerLogAspect
 * 并注入一个名为controllerLogAspect的bean来替换掉该默认实现
 * </pre>
 *
 * @author zhuwei
 * @date 2019/2/22 10:05
 */
@Aspect
@ConditionalOnMissingBean(name = "controllerLogAspect")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerLogAspect extends AbstractControllerLogAspect {

    @Pointcut("execution(* *..controller.*.*(..))")
    @Override
    public void controllerLog() {
        // do nothing here.
    }
}
