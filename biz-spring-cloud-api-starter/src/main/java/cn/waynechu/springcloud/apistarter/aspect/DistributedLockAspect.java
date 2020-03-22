package cn.waynechu.springcloud.apistarter.aspect;

import cn.waynechu.bootstarter.logger.filter.MDCFilter;
import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import cn.waynechu.springcloud.apistarter.properties.CommonProperty;
import cn.waynechu.springcloud.apistarter.properties.nested.DistributedLockProperty;
import cn.waynechu.springcloud.common.annotation.DistributedLock;
import cn.waynechu.springcloud.common.cache.RedisUtil;
import cn.waynechu.springcloud.common.util.DequeThreadLocal;
import cn.waynechu.springcloud.common.util.SpelUtil;
import cn.waynechu.springcloud.common.util.UUIDUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 分布式锁切面
 *
 * <pre>
 * 示例场景：
 * 1.分布式资源访问控制
 * 2.单位时间内接口访问控制
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
    private RedisUtil redisUtil;

    @Value("${spring.application.name}")
    private String applicationName;

    private static final DequeThreadLocal<DistributeLockHolder> DEQUE_THREAD_LOCAL = new DequeThreadLocal<>();

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

        // 锁全名格式: 应用名:分布式锁前缀:类名.方法名():锁关键字
        String lockFullName = applicationName + ":" + commonProperty.getDistributedLock().getPrefix() + ":" + lockName + ":" + lockKey;

        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestId = servletRequest.getHeader(MDCFilter.HEADER_KEY_REQUEST_ID);

        DistributeLockHolder lockHolder = new DistributeLockHolder();
        lockHolder.setLockFullName(lockFullName);
        lockHolder.setRequestId(requestId == null ? UUIDUtil.getShortUUID() : requestId);
        DEQUE_THREAD_LOCAL.offerFirst(lockHolder);

        Boolean getLock = redisUtil.getLock(lockFullName, requestId, distributedLock.expire(), distributedLock.timeUnit());
        if (Boolean.FALSE.equals(getLock)) {
            throw new BizException(BizErrorCodeEnum.RESOURCE_HAS_BEEN_LOCKED);
        }
    }

    @After("@annotation(distributedLock)")
    public void doAfter(DistributedLock distributedLock) {
        if (distributedLock.autoRelease()) {
            DistributeLockHolder localHolder = DEQUE_THREAD_LOCAL.pollFirst();
            if (localHolder != null) {
                redisUtil.delLock(localHolder.getLockFullName(), localHolder.getRequestId());
            }
        }
    }

    @Data
    private static class DistributeLockHolder {
        private String lockFullName;
        private String requestId;
    }

}
