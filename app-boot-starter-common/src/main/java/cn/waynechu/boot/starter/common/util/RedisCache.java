package cn.waynechu.boot.starter.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuwei
 * @date 2018/12/28 16:09
 */
@Slf4j
public class RedisCache {
    private String prefix;
    private boolean redisPrintOps;
    private RedisTemplate<String, Object> redisTemplate;
    private ValueOperations<String, Object> valueOperations;
    private HashOperations<String, Object, Object> hashOperations;
    private ListOperations<String, Object> listOperations;
    private SetOperations<String, Object> setOperations;
    private ZSetOperations<String, Object> zSetOperations;
    private GeoOperations<String, Object> geoOperations;
    private HyperLogLogOperations<String, Object> hyperLogLogOperations;

    public RedisCache(String prefix, boolean redisPrintOps, RedisTemplate<String, Object> redisTemplate) {
        this.prefix = prefix;
        this.redisPrintOps = redisPrintOps;
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
        this.hashOperations = redisTemplate.opsForHash();
        this.listOperations = redisTemplate.opsForList();
        this.setOperations = redisTemplate.opsForSet();
        this.zSetOperations = redisTemplate.opsForZSet();
        this.geoOperations = redisTemplate.opsForGeo();
        this.hyperLogLogOperations = redisTemplate.opsForHyperLogLog();
    }


    /**
     * 根据key获取指定类型的value
     *
     * @param key   k
     * @param clazz 返回值类类型
     * @param <T>   返回值类型
     * @return v
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        if (redisPrintOps) {
            log.info("开始从redis取得数据并封成对象返回: key = {}", keyWithPrefix);
        }

        T value = null;
        try {
            value = (T) valueOperations.get(keyWithPrefix);
            if (redisPrintOps) {
                log.info("从redis取得数据并封成对象返回结束: key = {}, value = {}", keyWithPrefix, value);
            }
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("从redis取得数据失败: key = {}", keyWithPrefix, e);
            }
        }
        return value;
    }

    /**
     * 根据key获取指定列表类型的value
     *
     * @param key   k
     * @param clazz 返回值类类型
     * @param <T>   返回值类型
     * @return v
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getFromList(String key, Class<T> clazz) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        if (redisPrintOps) {
            log.info("开始从redis取得数据并封成对象返回: key = {}", keyWithPrefix);
        }

        List<T> value = null;
        try {
            value = (List<T>) valueOperations.get(keyWithPrefix);
            if (redisPrintOps) {
                log.info("从redis取得数据并封成对象返回结束: key = {}, value = {}", keyWithPrefix, value);
            }
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("从redis取得数据失败: key = {}", keyWithPrefix, e);
            }
        }
        return value;
    }

    /**
     * 添加键值对
     *
     * @param key   k
     * @param value v
     */
    public void set(String key, Object value) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        try {
            if (redisPrintOps) {
                log.info("开始向redis存储数据: key = {}, value = {}", keyWithPrefix, value);
            }
            valueOperations.set(keyWithPrefix, value);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("向redis存储数据失败: key = {}, value = {}", keyWithPrefix, value, e);
            }
            return;
        }
        if (redisPrintOps) {
            log.info("向redis存储数据结束: key = {}, value = {}", keyWithPrefix, value);
        }
    }

    /**
     * 添加键值对
     *
     * @param key    k
     * @param value  v
     * @param second 过期时间，单位秒
     */
    public void set(String key, Object value, long second) {
        this.set(key, value, second, TimeUnit.SECONDS);
    }

    /**
     * 添加键值对
     *
     * @param key     k
     * @param value   v
     * @param timeout 过期时间
     * @param unit    单位
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        try {
            if (redisPrintOps) {
                log.info("开始向redis存储数据: key = {}, value = {}, timeout = {}, unit = {}", keyWithPrefix, value, timeout, unit);
            }
            valueOperations.set(keyWithPrefix, value, timeout, unit);

            if (redisPrintOps) {
                log.info("向redis存储数据结束: key = {}, value = {}, timeout = {}, unit = {}", keyWithPrefix, value, timeout, unit);
            }
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("向redis存储数据失败: key = {}, value = {}, timeout = {}, unit = {}", keyWithPrefix, value, timeout, unit, e);
            }
        }
    }

    /**
     * 添加指定键值对(SET if Not exists)
     * 当key存在时，什么也不做
     * 如果key不存在，这种情况下等同SET命令
     *
     * @param key   k
     * @param value v
     * @return true 如果key被设置成功; false 如果key被设置失败
     */
    public Boolean setIfAbsent(String key, Object value) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        return valueOperations.setIfAbsent(keyWithPrefix, value);
    }

    /**
     * 添加指定键值对(SET if Not exists)
     * 当key存在时，什么也不做
     * 如果key不存在，这种情况下等同SET命令
     *
     * @param key    k
     * @param value  v
     * @param second 有效时间，单位秒
     * @return true 如果key被设置成功; false 如果key被设置失败
     */
    public Boolean setIfAbsent(String key, Object value, long second) {
        return this.setIfAbsent(key, value, second, TimeUnit.SECONDS);
    }

    /**
     * 添加指定键值对(SET if Not exists)
     * 当key存在时，什么也不做
     * 如果key不存在，这种情况下等同SET命令
     *
     * @param key     k
     * @param value   v
     * @param timeout 有效时间
     * @param unit    单位
     * @return true 如果key被设置成功; false 如果key被设置失败
     */
    public Boolean setIfAbsent(String key, Object value, long timeout, TimeUnit unit) {
        return valueOperations.setIfAbsent(getRedisKeyWithPrefix(key), value, timeout, unit);
    }

    /**
     * 将key对应的数字执行原子的加1操作
     * 如果key不存在，操作之前，key就会被置为0
     * 如果key的value类型错误或者是个不能表示成数字的字符串，就返回错误
     * 这个操作最多支持64位有符号的正型数字
     *
     * @param key k
     * @return decrement后的key
     */
    public Long increment(String key) {
        return valueOperations.increment(getRedisKeyWithPrefix(key));
    }

    /**
     * 将key对应的数字加decrement
     * 如果key不存在，操作之前，key就会被置为0
     * 如果key的value类型错误或者是个不能表示成数字的字符串，就返回错误
     * 这个操作最多支持64位有符号的正型数字
     *
     * @param key   k
     * @param delta 步长
     * @return decrement后的key
     */
    public Long increment(String key, long delta) {
        return valueOperations.increment(getRedisKeyWithPrefix(key), delta);
    }

    /**
     * 将key对应的数字加decrement
     * 如果key不存在，操作之前，key就会被置为0
     * 如果key的value类型错误或者是个不能表示成数字的字符串，就返回错误
     * 这个操作最多支持64位有符号的正型数字
     * <p>
     * 可用于计数场景
     *
     * @param key   k
     * @param delta decrement
     * @return decrement后的key
     */
    public Double increment(String key, double delta) {
        return valueOperations.increment(getRedisKeyWithPrefix(key), delta);
    }

    /**
     * 将key设置为value并返回原来的value值
     * 如果key存在但是对应的value不是字符串，就返回错误。
     * <p>
     * GETSET可以和INCR一起使用实现支持重置的计数功能
     *
     * @param key   k
     * @param value 返回之前的旧值，如果之前Key不存在将返回nil
     */
    @SuppressWarnings("unchecked")
    public <T> T getAndSet(String key, Object value, Class<T> clazz) {
        return (T) valueOperations.getAndSet(getRedisKeyWithPrefix(key), value);
    }

    /**
     * 删除指定键值对
     *
     * @param key k
     */
    public boolean delete(String key) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        if (redisPrintOps) {
            log.info("开始从redis删除数据: key = {}", keyWithPrefix);
        }

        boolean flag = false;
        try {
            flag = redisTemplate.delete(keyWithPrefix);
            if (redisPrintOps) {
                if (flag) {
                    log.info("从redis删除数据成功: key = {}", keyWithPrefix);
                } else {
                    log.error("value不存在: key = {}", keyWithPrefix);
                }
            }
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("从redis删除数据失败: key = {}", keyWithPrefix, e);
            }
        }
        return flag;
    }

    /**
     * 批量删除键
     *
     * @param keys ks
     * @return 删除数量
     */
    public Long delete(List<String> keys) {
        List<String> keysWithPrefix = new ArrayList<>(keys.size());
        for (String key : keys) {
            keysWithPrefix.add(getRedisKeyWithPrefix(key));
        }
        return redisTemplate.delete(keysWithPrefix);
    }

    /**
     * 判断键值对是否存在
     *
     * @param key k
     * @return 存在: true
     */
    public boolean isExist(String key) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        return redisTemplate.hasKey(keyWithPrefix);
    }

    /**
     * 获取分布式锁
     *
     * @param lockName     锁名
     * @param requestId    解锁标识，会将它作为value来存储。
     *                     可使用UUID.randomUUID().toString()或者其它唯一值标识
     * @param milliseconds 锁定有效期，单位毫秒
     *                     它不仅是key自动失效时间，还是一个客户端持有锁多长时间后
     *                     可以被另外一个客户端重新获得的时间
     * @return 加锁成功：true
     */
    public boolean getLock(String lockName, String requestId, long milliseconds) {
        String lockNameWithPrefix = getRedisKeyWithPrefix(lockName);
        return redisTemplate.opsForValue().setIfAbsent(lockNameWithPrefix, requestId, milliseconds, TimeUnit.MILLISECONDS);
    }

    /**
     * 移除分布式锁
     * <p>
     * 使用lua脚本实现分布式锁的移除，这种方式释放锁可以避免删除别的客户端获取成功的锁。
     * 脚本仅会删除requestId等于获取琐时传入的requestId
     *
     * @param lockName  锁名
     * @param requestId 解锁标识
     * @return 移除成功：true
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

        List<String> keyList = Collections.singletonList(getRedisKeyWithPrefix(lockName));

        Long execute = redisTemplate.execute(defaultRedisScript, keyList, requestId);
        return Long.valueOf(1).equals(execute);
    }

    /**
     * 获取分布式锁
     *
     * @param lockName    锁名
     * @param lockTimeout 过期时间，单位毫秒
     * @return 获取到锁返回锁过期的时间戳，未获取到返回-1
     */
    @Deprecated
    public long getRedisLock(String lockName, long lockTimeout) {
        long expireTime = System.currentTimeMillis() + lockTimeout + 1;
        String lockNameWithPrefix = getRedisKeyWithPrefix(lockName);
        // 尝试获取锁
        Boolean getLock = valueOperations.setIfAbsent(lockNameWithPrefix, String.valueOf(expireTime));
        if (getLock) {
            // 获取锁成功
            return expireTime;
        } else {
            // 未获取到锁。继续获取锁的过期时间
            String lockValue = (String) valueOperations.get(lockNameWithPrefix);
            if (lockValue != null && System.currentTimeMillis() > Long.parseLong(lockValue)) {
                // 该锁已超时，设置新值并返回旧值
                long newExpireTime = System.currentTimeMillis() + lockTimeout + 1;
                String getSetValue = (String) valueOperations.getAndSet(lockNameWithPrefix, String.valueOf(newExpireTime));
                if (getSetValue == null || (getSetValue != null && lockValue.equals(getSetValue))) {
                    // 已过期的旧值仍然存在。只有最先执行getAndSet()的应用进程才能获取到锁
                    return newExpireTime;
                }
            }
        }
        return -1;
    }

    /**
     * 移除分布式锁
     *
     * @param lockName   锁名
     * @param expireTime 锁过期的时间戳
     */
    @Deprecated
    public void delRedisLock(String lockName, long expireTime) {
        if (System.currentTimeMillis() < expireTime) {
            // 保证使用DEL释放锁之前不会过期
            String lockNameWithPrefix = getRedisKeyWithPrefix(lockName);
            redisTemplate.delete(lockNameWithPrefix);
        }
    }

    /**
     * 获取添加前缀后的key
     *
     * @param key 原始key
     * @return prefix:key。如果prefix不存在，直接返回原始key
     */
    private String getRedisKeyWithPrefix(String key) {
        if (!StringUtils.hasText(key)) {
            throw new IllegalArgumentException("key不能为空");
        }

        if (StringUtils.hasText(this.prefix)) {
            return this.prefix + ":" + key;
        } else {
            return key;
        }
    }

    public String getPrefix() {
        return prefix;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public ValueOperations<String, Object> getValueOperations() {
        return valueOperations;
    }

    public HashOperations<String, Object, Object> getHashOperations() {
        return hashOperations;
    }

    public ListOperations<String, Object> getListOperations() {
        return listOperations;
    }

    public SetOperations<String, Object> getSetOperations() {
        return setOperations;
    }

    public ZSetOperations<String, Object> getzSetOperations() {
        return zSetOperations;
    }

    public GeoOperations<String, Object> getGeoOperations() {
        return geoOperations;
    }

    public HyperLogLogOperations<String, Object> getHyperLogLogOperations() {
        return hyperLogLogOperations;
    }
}
