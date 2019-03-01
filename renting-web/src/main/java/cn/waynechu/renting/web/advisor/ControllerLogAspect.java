package cn.waynechu.renting.web.advisor;

import cn.waynechu.webcommon.aspect.BaseMethodPrintAspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Controller层日志切面实现
 * 过滤HttpServletResponse、MultipartFile类型参数
 *
 * @author zhuwei
 * @date 2019/2/22 10:05
 */
@Aspect
@Component
public class ControllerLogAspect extends BaseMethodPrintAspect {

    @Pointcut("@annotation(cn.waynechu.webcommon.annotation.MethodPrintAnnotation)")
    @Override
    public void methodPrint() {
        // do nothing here.
    }

    @Override
    protected Collection<Class> excludePrintClass() {
        ArrayList<Class> defaultExcludeClasses = new ArrayList<>(2);
        defaultExcludeClasses.add(HttpServletResponse.class);
        defaultExcludeClasses.add(MultipartFile.class);
        return defaultExcludeClasses;
    }
}
