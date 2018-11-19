package cn.waynechu.renting.web.aspect;

import cn.waynechu.common.aspect.AbstractControllerLogAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author zhuwei
 * @date 2018/11/14 14:57
 */
@Aspect
@Component
public class ControllerLogAspect extends AbstractControllerLogAspect {

    @Pointcut("execution(* cn.waynechu.app.web.controller.*Controller.*(..))")
    @Override
    public void controllerLog() {
        //
    }

    @Override
    protected Object getRequestData(JoinPoint joinPoint) {
        return joinPoint.getArgs();
    }

    @Override
    protected Object getResultData(Object result) {
        return result;
    }
}
