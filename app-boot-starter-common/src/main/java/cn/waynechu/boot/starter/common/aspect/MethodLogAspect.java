package cn.waynechu.boot.starter.common.aspect;

import cn.waynechu.webcommon.aspect.AbstractMethodLogAspect;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

/**
 * 方法调用情况监控切面默认实现
 * <p>
 * 在方法加上@MethodLogAnnotation注解即可
 *
 * @author zhuwei
 * @date 2019/3/29 17:10
 */
@Aspect
@ConditionalOnMissingBean(name = "methodLogAspect")
public class MethodLogAspect extends AbstractMethodLogAspect {
}
