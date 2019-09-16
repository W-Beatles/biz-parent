package cn.waynechu.springcloud.apicommon.aspect;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import cn.waynechu.springcloud.apicommon.cache.RedisCache;
import cn.waynechu.springcloud.common.annotation.DistributedLock;
import cn.waynechu.springcloud.common.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhuwei
 * @date 2019/9/11 11:14
 */
@Slf4j
@Aspect
public class DistributedLockAspect {

    @Autowired
    private RedisCache redisCache;

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Before("@annotation(distributedLock)")
    public void doBefore(DistributedLock distributedLock) {
        String requestId = UUIDUtil.getRandomUUID();
        DistributedLockAspect.threadLocal.set(requestId);

        String lockName = distributedLock.name() + distributedLock.key();
        Boolean getLock = redisCache.getLock(lockName, requestId, distributedLock.expire(), distributedLock.timeUnit());
        if (Boolean.FALSE.equals(getLock)) {
            throw new BizException(BizErrorCodeEnum.TOO_MANY_REQUEST);
        }
    }
}
