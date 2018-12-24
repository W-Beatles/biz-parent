package cn.waynechu.renting.web.aspect;

import cn.waynechu.webcommon.aspect.AbstractMethodPrintAspect;
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
    private static final String POINTCUT_METHOD_EXPRESSION = "execution(* cn.waynechu.renting.web.controller.*Controller.*(..))";

    @Pointcut(POINTCUT_METHOD_EXPRESSION)
    @Override
    public void methodPrint() {
        //
    }
}
