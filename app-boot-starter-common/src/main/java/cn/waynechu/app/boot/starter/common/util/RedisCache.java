package cn.waynechu.app.boot.starter.common.util;

import cn.waynechu.webcommon.util.JsonBinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;

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
    private StringRedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOperations;
    private HashOperations<String, Object, Object> hashOperations;

    public RedisCache(String prefix, boolean redisPrintOps, StringRedisTemplate redisTemplate) {
        this.prefix = prefix;
        this.redisPrintOps = redisPrintOps;
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
        this.hashOperations = redisTemplate.opsForHash();
    }

    public <T> T get(String key, Class<T> clazz) {
        if (redisPrintOps) {
            log.info("开始从redis取得数据并封成对象返回:key={}", key);
        }

        if (!StringUtils.hasText(key)) {
            return null;
        }

        T result = null;
        try {
            String resultStr = valueOperations.get(key);
            if (!StringUtils.hasText(resultStr)) {
                return null;
            }
            result = JsonBinder.buildAlwaysBinder().fromJson(resultStr, clazz);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.info("从redis取得数据失败:key={}", key);
            }
            return null;
        }

        if (redisPrintOps) {
            log.info("从redis取得数据并封成对象返回结束:key={},result={}", key, result);
        }
        return result;
    }

    public String get(String key) {
        if (redisPrintOps) {
            log.info("开始从redis取得数据并封成对象返回:key={}", key);
        }

        String result = null;
        try {
            result = valueOperations.get(key);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.info("从redis取得数据失败:key={}", key, e);
            }
            return null;
        }

        if (redisPrintOps) {
            log.info("开始从redis取得数据并封成对象返回: key={},result={}", key, result);
        }
        return result;
    }

    public void set(String key, String json) {
        try {
            if (redisPrintOps) {
                log.info("开始向redis存储数据:key={},value={}", key, json);
            }
            valueOperations.set(key, json);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.info("向redis存储数据失败:key={},value={}", key, json);
            }
            return;
        }

        if (redisPrintOps) {
            log.info("向redis存储数据结束:key={},value={}", key, json);
        }
    }

    public void set(String key, String json, long timeout) {
        this.set(key, json, timeout, TimeUnit.SECONDS);
    }

    public void set(String key, String json, long timeout, TimeUnit unit) {
        try {
            if (redisPrintOps) {
                log.info("开始向redis存储数据:key={},value={},timeout={},unit={}", key, json, timeout, unit);
            }
            valueOperations.set(key, json, timeout, unit);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.info("向redis存储数据失败:key={},value={},timeout={},unit={}", key, json, timeout, unit);
            }
            return;
        }

        if (redisPrintOps) {
            log.info("向redis存储数据结束:key={},value={},timeout={},unit={}", key, json, timeout, unit);
        }
    }

    public void set(String key, Object obj) {
        try {
            if (redisPrintOps) {
                log.info("开始向redis存储数据:key={},value={}", key, obj);
            }
            valueOperations.set(key, JsonBinder.buildAlwaysBinder().toJson(obj));
        } catch (Exception e) {
            if (redisPrintOps) {
                log.info("向redis存储数据失败:key={},value={}", key, obj);
            }
            return;
        }
        if (redisPrintOps) {
            log.info("向redis存储数据结束:key={},value={}", key, obj);
        }
    }

    public void set(String key, Object obj, long timeout) {
        this.set(key, obj, timeout, TimeUnit.SECONDS);
    }

    public void set(String key, Object obj, long timeout, TimeUnit unit) {
        try {
            if (redisPrintOps) {
                log.info("开始向redis存储数据:key={},value={},timeout={},unit={}", key, obj, timeout, unit);
            }
            valueOperations.set(key, JsonBinder.buildAlwaysBinder().toJson(obj), timeout, unit);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.info("向redis存储数据失败:key={},value={},timeout={},unit={}", key, obj, timeout, unit);
            }
            return;
        }
        if (redisPrintOps) {
            log.info("向redis存储数据结束:key={},value={},timeout={},unit={}", key, obj, timeout, unit);
        }
    }

    public void set(String key, String hashKey, String json) {
        try {
            if (redisPrintOps) {
                log.info("开始向redis存储数据:key={},hashKey={},value={}", key, hashKey, json);
            }
            hashOperations.put(key, hashKey, json);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.info("向redis存储数据失败:key={},hashKey={},value={}", key, hashKey, json);
            }
            return;
        }
        if (redisPrintOps) {
            log.info("向redis存储数据结束:key={},hashKey={},value={}", key, hashKey, json);
        }
    }

    public void set(String key, String hashKey, Object obj) {
        try {
            if (redisPrintOps) {
                log.info("开始向redis存储数据:key={},hashKey={},value={}", key, hashKey, obj);
            }
            hashOperations.put(key, hashKey, JsonBinder.buildAlwaysBinder().toJson(obj));
        } catch (Exception e) {
            if (redisPrintOps) {
                log.info("向redis存储数据失败:key={},hashKey={},value={}", key, hashKey, obj, e);
            }
            return;
        }
        if (redisPrintOps) {
            log.info("向redis存储数据结束:key={},hashKey={},value={}", key, hashKey, obj);
        }
    }

    public Object get(String key, String hashKey) {
        if (redisPrintOps) {
            log.info("开始从redis取得数据:key={},hashKey={}", key, hashKey);
        }

        Object result = null;
        try {
            result = hashOperations.get(key, hashKey);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.info("从redis取得数据异常:key={},hashKey={}", key, hashKey, e);
            }
            return null;
        }

        if (redisPrintOps) {
            log.info("从redis取得数据并封成对象结束:key={},hashKey={},result={}", key, hashKey, result);
        }
        return result;
    }

    public <T> T getWithInstance(String key, String hashKey, Class<? extends T> clazz) {
        if (redisPrintOps) {
            log.info("开始从redis取得数据:key={},hashKey={}", key, hashKey);
        }

        if (!StringUtils.hasText(hashKey)) {
            return null;
        }

        T result = null;
        try {
            Object resultStr = hashOperations.get(key, hashKey);
            //如果为空,直接返回null
            if (resultStr == null || !StringUtils.hasText(resultStr + "")) {
                return null;
            }
            result = JsonBinder.buildAlwaysBinder().fromJson(resultStr + "", clazz);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.info("从redis取得数据失败:key={},hashKey={}", key, hashKey, e);
            }
            return null;
        }

        if (redisPrintOps) {
            log.info("从redis取得数据并封成对象结束:key={},hashKey={},result={}", key, hashKey, result);
        }

        return result;
    }

    public void hashDelete(String key, String hashKey) {
        if (redisPrintOps) {
            log.info("开始删除redis缓存数据:key={},hashKey={}", key, hashKey);
        }

        boolean flag = false;
        try {
            flag = hashOperations.get(key, hashKey) != null;
            if (flag) {
                hashOperations.delete(key, hashKey);
            }
        } catch (Exception e) {
            if (redisPrintOps) {
                log.info("删除redis缓存数据失败:key={},hashKey={}", key, hashKey);
            }
            return;
        }

        if (redisPrintOps) {
            log.info("删除redis缓存数据结束:key={},hashKey={}", key, hashKey);
        }
    }

    public void append(String key, String json) {
        if (redisPrintOps) {
            log.info("开始向redis中追加数据:key={}", key);
        }

        try {
            valueOperations.append(key, json);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.info("向redis中追加数据失败:key={}", key, e);
            }
            return;
        }
        if (redisPrintOps) {
            log.info("向redis中追加数据结束:key={}", key);
        }
    }

    public void delete(String key) {
        if (redisPrintOps) {
            log.info("开始从redis删除数据:key={}", key);
        }

        boolean flag = false;
        try {
            flag = valueOperations.get(key) != null;
            if (flag) {
                redisTemplate.delete(key);
            }
        } catch (Exception e) {
            if (redisPrintOps) {
                log.info("从redis删除数据异常:key={}", key, e);
            }
            return;
        }

        if (redisPrintOps) {
            log.info("从redis删除数据结束:key={}", key);
        }
    }

    public <T> List<T> getToList(String key, Class<T> clazz) {
        if (redisPrintOps) {
            log.info("开始从redis取得数据并封成list返回:key={}", key);
        }

        List<T> result = null;
        try {
            String resultstr = valueOperations.get(key);
            if (!StringUtils.hasText(resultstr)) {
                return null;
            }
            result = JsonBinder.buildAlwaysBinder().fromJsonList(resultstr, clazz);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.info("从redis取得数据失败:key={}", key, e);
            }
            return null;
        }

        if (redisPrintOps) {
            log.info("开始从redis取得数据并封成对象返回结束:key={},result={}", key, result);
        }
        return result;
    }

    public <T> List<T> hashGetToList(String key, String hashKey, Class<T> clazz) {
        if (redisPrintOps) {
            log.info("开始从redis取得数据:key={},hashKey={}", key, hashKey);
        }

        if (!StringUtils.hasText(key) || !StringUtils.hasText(hashKey)) {
            return null;
        }

        List<T> result = null;
        try {
            Object resultobj = hashOperations.get(key, hashKey);
            //如果为空,直接返回null
            result = JsonBinder.buildAlwaysBinder().fromJsonList(resultobj + "", clazz);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.info("从redis取得数据失败:key={},hashKey={}", key, hashKey, e);
            }
            return null;
        }

        if (redisPrintOps) {
            log.info("从redis取得数据结束:key={},hashKey={}", key, hashKey);
        }
        return result;
    }

    public boolean isExist(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.info("从redis取得数据失败:key={},hashKey={}", key, e);
            }
            return false;
        }
    }

    public String getRedisKey(String key) {
        if (StringUtils.hasText(prefix)) {
            return this.prefix + key;
        } else {
            return key;
        }
    }
}
