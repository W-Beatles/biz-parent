package cn.waynechu.renting.web.aspect;

import cn.waynechu.common.aspect.AbstractMethodPrintAspect;
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
public class ControllerLogAspect extends AbstractMethodPrintAspect {

    @Pointcut("execution(* cn.waynechu.renting.web.controller.*Controller.*(..))")
    @Override
    public void methodPrint() {
        //
    }

    @Override
    protected Object getArgs(JoinPoint joinPoint) {
        return joinPoint.getArgs();
    }

    @Override
    protected Object getReturn(Object result) {
        return result;
    }
}
