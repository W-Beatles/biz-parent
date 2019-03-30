package cn.waynechu.boot.starter.common.aspect;

import cn.waynechu.webcommon.aspect.AbstractControllerLogAspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

/**
 * Controller层日志切面默认实现
 *
 * <pre>
 * 切面生效条件:
 * 1. 该类位于controller包下
 * 2. 添加了{@code io.swagger.annotations.ApiOperation} 注解
 * </pre>
 *
 * @author zhuwei
 * @date 2019/2/22 10:05
 */
@Aspect
@ConditionalOnMissingBean(name = "controllerLogAspect")
public class ControllerLogAspect extends AbstractControllerLogAspect {

    @Pointcut("execution(* *..controller.*.*(..))")
    @Override
    public void controllerLog() {
        // do nothing here.
    }
}
