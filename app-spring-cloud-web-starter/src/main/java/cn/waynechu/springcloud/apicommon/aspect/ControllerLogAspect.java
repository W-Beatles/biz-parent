package cn.waynechu.springcloud.apicommon.aspect;

import cn.waynechu.spirngcloud.common.aspect.AbstractControllerLogAspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Controller层日志切面实现
 *
 * @author zhuwei
 * @date 2019/2/22 10:05
 */
@Aspect
@Component
public class ControllerLogAspect extends AbstractControllerLogAspect {

    @Pointcut("execution(* cn.waynechu..*.*(..))")
    @Override
    public void controllerLog() {
        // do nothing here.
    }
}
