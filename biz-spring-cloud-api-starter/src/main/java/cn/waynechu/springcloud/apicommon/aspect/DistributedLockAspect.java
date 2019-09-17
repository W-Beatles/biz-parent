package cn.waynechu.springcloud.apicommon.aspect;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import cn.waynechu.springcloud.apicommon.cache.RedisCache;
import cn.waynechu.springcloud.apicommon.properties.CommonProperty;
import cn.waynechu.springcloud.apicommon.properties.DistributedLockProperty;
import cn.waynechu.springcloud.common.annotation.DistributedLock;
import cn.waynechu.springcloud.common.util.SpelUtil;
import cn.waynechu.springcloud.common.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * 方法访问控制切面
 *
 * <pre>
 * 示例场景：
 * 1.接口因为前端按钮的多次点击重复请求的问题
 *
 * 切面生效条件:
 * 1. biz.api.starter.distributed-lock.enable=true
 * 1. 方法上添加 {@code cn.waynechu.springcloud.common.annotation.DistributedLock} 注解
 * </pre>
 *
 * @author zhuwei
 * @date 2019/9/11 11:14
 */
@Slf4j
@Aspect
@ConditionalOnProperty(value = DistributedLockProperty.DISTRIBUTED_LOCK_CONFIG_PREFIX + ".enable", havingValue = "true")
public class DistributedLockAspect {

    @Autowired
    private CommonProperty commonProperty;

    @Autowired
    private RedisCache redisCache;

    @Value("${spring.application.name}")
    private String applicationName;

    @Before("@annotation(distributedLock)")
    public void doBefore(JoinPoint joinPoint, DistributedLock distributedLock) {
        String lockName = distributedLock.name();
        if ("".equals(lockName)) {
            // lockName格式为 类名.方法名()
            lockName = joinPoint.getTarget().getClass().getName() + "." +
                    ((MethodSignature) joinPoint.getSignature()).getMethod().getName() + "()";
        }

        String lockKey;
        String spelKey = distributedLock.key();
        if (!SpelUtil.isSpelEx(spelKey)) {
            lockKey = spelKey;
        } else {
            String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
            Object[] args = joinPoint.getArgs();
            lockKey = SpelUtil.getKey(spelKey, parameterNames, args);
        }

        String lockFullName = commonProperty.getDistributedLock().getPrefix() + ":" + lockName + ":" + lockKey;

        Boolean getLock = redisCache.getLock(lockFullName, UUIDUtil.getRandomUUID(), distributedLock.expire(), distributedLock.timeUnit());
        if (Boolean.FALSE.equals(getLock)) {
            throw new BizException(BizErrorCodeEnum.TOO_MANY_REQUEST);
        }
    }
}
