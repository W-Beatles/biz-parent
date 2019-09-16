package cn.waynechu.springcloud.apicommon.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuwei
 * @date 2018/12/28 16:09
 */
@Slf4j
@Component
public class RedisCache {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public RedisCache(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取分布式锁
     *
     * @param lockName     锁名
     * @param requestId    解锁标识，会将它作为value来存储
     *                     可使用UUID.randomUUID().toString()或者其它唯一值标识
     * @param milliseconds 锁定有效期，单位毫秒
     *                     它不仅是key自动失效时间，还是一个客户端持有锁多长时间后
     *                     可以被另外一个客户端重新获得的时间
     * @return 获取锁成功返回 {@code true}
     */
    public Boolean getLock(String lockName, String requestId, long milliseconds) {
        return redisTemplate.opsForValue().setIfAbsent(lockName, requestId, milliseconds, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取分布式锁
     *
     * @param lockName  锁名
     * @param requestId 解锁标识，会将它作为value来存储
     *                  可使用UUID.randomUUID().toString()或者其它唯一值标识
     * @param timeout   锁定有效期
     *                  它不仅是key自动失效时间，还是一个客户端持有锁多长时间后
     *                  可以被另外一个客户端重新获得的时间
     * @param unit      时间单位
     * @return 获取锁成功返回 {@code true}
     */
    public Boolean getLock(String lockName, String requestId, long timeout, TimeUnit unit) {
        return redisTemplate.opsForValue().setIfAbsent(lockName, requestId, timeout, unit);
    }

    /**
     * 移除分布式锁
     * <p>
     * 使用lua脚本实现分布式锁的移除，这种方式释放锁可以避免删除别的客户端获取成功的锁。
     * 脚本仅会删除requestId等于获取琐时传入的requestId
     *
     * @param lockName  锁名
     * @param requestId 解锁标识
     * @return {@code true} 移除成功
     */
    public boolean delLock(String lockName, String requestId) {
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Long.class);
        defaultRedisScript.setScriptText(
                "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                        "    return redis.call(\"del\",KEYS[1])\n" +
                        "else\n" +
                        "    return 0\n" +
                        "end");

        List<String> keyList = Collections.singletonList(lockName);
        Long execute = redisTemplate.execute(defaultRedisScript, keyList, requestId);
        return Long.valueOf(1).equals(execute);
    }

    /**
     * 获取分布式锁
     *
     * @param lockName    锁名
     * @param lockTimeout 过期时间，单位毫秒
     * @return 获取到锁返回锁过期的时间戳，未获取到返回-1
     * @deprecated 请使用最新的 {@code getLock()} 方法。本方法需要服务器时间同步，并且有出现死锁的可能
     */
    @Deprecated
    public long getRedisLock(String lockName, long lockTimeout) {
        long expireTime = System.currentTimeMillis() + lockTimeout + 1;
        // 尝试获取锁
        Boolean getLock = redisTemplate.opsForValue().setIfAbsent(lockName, String.valueOf(expireTime));
        if (Boolean.TRUE.equals(getLock)) {
            // 获取锁成功
            return expireTime;
        } else {
            // 未获取到锁。继续获取锁的过期时间
            String lockValue = redisTemplate.opsForValue().get(lockName);
            if (lockValue != null && System.currentTimeMillis() > Long.parseLong(lockValue)) {
                // 该锁已超时，设置新值并返回旧值
                long newExpireTime = System.currentTimeMillis() + lockTimeout + 1;
                String getSetValue = redisTemplate.opsForValue().getAndSet(lockName, String.valueOf(newExpireTime));
                if (getSetValue == null || lockValue.equals(getSetValue)) {
                    // 已过期的旧值仍然存在。只有最先执行getAndSet()的应用进程才能获取到锁
                    return newExpireTime;
                }
            }
        }
        return -1;
    }
}
