package cn.waynechu.springcloud.apicommon.aspect;

import cn.waynechu.springcloud.common.aspect.AbstractMethodLogAspect;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

/**
 * 方法调用情况监控切面默认实现
 *
 * <pre>
 * 切面生效条件:
 * 1. 方法上添加了{@code cn.waynechu.springcloud.common.annotation.MethodLogAnnotation} 注解
 * </pre>
 *
 * @author zhuwei
 * @date 2019/3/29 17:10
 */
@Aspect
@ConditionalOnMissingBean(name = "methodLogAspect")
public class MethodLogAspect extends AbstractMethodLogAspect {
}
