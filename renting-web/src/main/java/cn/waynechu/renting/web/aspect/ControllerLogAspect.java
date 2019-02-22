package cn.waynechu.renting.web.aspect;

import cn.waynechu.webcommon.aspect.BaseMethodPrintAspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author zhuwei
 * @date 2019/2/22 10:05
 */
@Aspect
@Component
public class ControllerLogAspect extends BaseMethodPrintAspect {

    @Pointcut("execution(* cn.waynechu.renting.web.controller.*Controller.*(..))")
    @Override
    public void methodPrint() {
        // NOPE
    }

    @Override
    protected Collection<Class> excludePrintClass() {
        ArrayList<Class> defaultExcludeClasses = new ArrayList<>(2);
        defaultExcludeClasses.add(HttpServletResponse.class);
        defaultExcludeClasses.add(MultipartFile.class);
        return defaultExcludeClasses;
    }
}
