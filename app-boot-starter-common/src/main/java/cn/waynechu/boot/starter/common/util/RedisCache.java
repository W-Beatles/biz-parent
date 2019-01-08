package cn.waynechu.boot.starter.common.util;

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
        if (!StringUtils.hasText(key)) {
            return null;
        }

        String keyWithPrefix = getRedisKeyWithPrefix(key);
        if (redisPrintOps) {
            log.info("开始从redis取得数据并封成对象返回: key={}", keyWithPrefix);
        }

        T result = null;
        try {
            String resultStr = valueOperations.get(keyWithPrefix);
            if (!StringUtils.hasText(resultStr)) {
                return null;
            }
            result = JsonBinder.buildAlwaysBinder().toJavaObjectFromJson(resultStr, clazz);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("从redis取得数据失败: key={}", keyWithPrefix, e);
            }
            return null;
        }

        if (redisPrintOps) {
            log.info("从redis取得数据并封成对象返回结束: key={}, result={}", keyWithPrefix, result);
        }
        return result;
    }

    public String get(String key) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        if (redisPrintOps) {
            log.info("开始从redis取得数据并封成对象返回: key={}", keyWithPrefix);
        }

        String result = null;
        try {
            result = valueOperations.get(keyWithPrefix);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("从redis取得数据失败: key={}", keyWithPrefix, e);
            }
            return null;
        }

        if (redisPrintOps) {
            log.info("开始从redis取得数据并封成对象返回: key={}, result={}", keyWithPrefix, result);
        }
        return result;
    }

    public void set(String key, String json) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        try {
            if (redisPrintOps) {
                log.info("开始向redis存储数据: key={}, value={}", keyWithPrefix, json);
            }
            valueOperations.set(keyWithPrefix, json);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("向redis存储数据失败: key={}, value={}", keyWithPrefix, json, e);
            }
            return;
        }

        if (redisPrintOps) {
            log.info("向redis存储数据结束: key={}, value={}", keyWithPrefix, json);
        }
    }

    public void set(String key, String json, long timeout) {
        this.set(key, json, timeout, TimeUnit.SECONDS);
    }

    public void set(String key, String json, long timeout, TimeUnit unit) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        try {
            if (redisPrintOps) {
                log.info("开始向redis存储数据: key={}, value={}, timeout={}, unit={}", keyWithPrefix, json, timeout, unit);
            }
            valueOperations.set(keyWithPrefix, json, timeout, unit);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("向redis存储数据失败: key={}, value={}, timeout={}, unit={}", keyWithPrefix, json, timeout, unit, e);
            }
            return;
        }

        if (redisPrintOps) {
            log.info("向redis存储数据结束: key={}, value={}, timeout={}, unit={}", keyWithPrefix, json, timeout, unit);
        }
    }

    public void set(String key, Object obj) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        try {
            if (redisPrintOps) {
                log.info("开始向redis存储数据: key={}, value={}", keyWithPrefix, obj);
            }
            valueOperations.set(keyWithPrefix, JsonBinder.buildAlwaysBinder().toJson(obj));
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("向redis存储数据失败: key={}, value={}", keyWithPrefix, obj, e);
            }
            return;
        }
        if (redisPrintOps) {
            log.info("向redis存储数据结束: key={},value={}", keyWithPrefix, obj);
        }
    }

    public void set(String key, Object obj, long timeout) {
        this.set(key, obj, timeout, TimeUnit.SECONDS);
    }

    public void set(String key, Object obj, long timeout, TimeUnit unit) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        try {
            if (redisPrintOps) {
                log.info("开始向redis存储数据: key={}, value={}, timeout={}, unit={}", keyWithPrefix, obj, timeout, unit);
            }
            valueOperations.set(keyWithPrefix, JsonBinder.buildAlwaysBinder().toJson(obj), timeout, unit);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("向redis存储数据失败: key={}, value={}, timeout={}, unit={}", keyWithPrefix, obj, timeout, unit, e);
            }
            return;
        }
        if (redisPrintOps) {
            log.info("向redis存储数据结束: key={}, value={}, timeout={}, unit={}", keyWithPrefix, obj, timeout, unit);
        }
    }

    public void set(String key, String hashKey, String json) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        try {
            if (redisPrintOps) {
                log.info("开始向redis存储数据: key={}, hashKey={}, value={}", keyWithPrefix, hashKey, json);
            }
            hashOperations.put(keyWithPrefix, hashKey, json);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("向redis存储数据失败: key={}, hashKey={}, value={}", keyWithPrefix, hashKey, json, e);
            }
            return;
        }
        if (redisPrintOps) {
            log.info("向redis存储数据结束: key={}, hashKey={}, value={}", keyWithPrefix, hashKey, json);
        }
    }

    public void set(String key, String hashKey, Object obj) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        try {
            if (redisPrintOps) {
                log.info("开始向redis存储数据: key={}, hashKey={}, value={}", keyWithPrefix, hashKey, obj);
            }
            hashOperations.put(keyWithPrefix, hashKey, JsonBinder.buildAlwaysBinder().toJson(obj));
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("向redis存储数据失败: key={}, hashKey={}, value={}", keyWithPrefix, hashKey, obj, e);
            }
            return;
        }
        if (redisPrintOps) {
            log.info("向redis存储数据结束: key={}, hashKey={}, value={}", keyWithPrefix, hashKey, obj);
        }
    }

    public Object get(String key, String hashKey) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        if (redisPrintOps) {
            log.info("开始从redis取得数据: key={}, hashKey={}", keyWithPrefix, hashKey);
        }

        Object result = null;
        try {
            result = hashOperations.get(keyWithPrefix, hashKey);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("从redis取得数据失败: key={}, hashKey={}", keyWithPrefix, hashKey, e);
            }
            return null;
        }

        if (redisPrintOps) {
            log.info("从redis取得数据并封成对象结束: key={}, hashKey={}, result={}", keyWithPrefix, hashKey, result);
        }
        return result;
    }

    public <T> T getWithInstance(String key, String hashKey, Class<? extends T> clazz) {
        if (!StringUtils.hasText(hashKey)) {
            return null;
        }

        String keyWithPrefix = getRedisKeyWithPrefix(key);
        if (redisPrintOps) {
            log.info("开始从redis取得数据: key={}, hashKey={}", keyWithPrefix, hashKey);
        }

        T result = null;
        try {
            Object resultStr = hashOperations.get(keyWithPrefix, hashKey);
            // 如果为空,直接返回null
            if (resultStr == null || !StringUtils.hasText(resultStr + "")) {
                return null;
            }
            result = JsonBinder.buildAlwaysBinder().toJavaObjectFromJson(resultStr + "", clazz);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("从redis取得数据失败: key={}, hashKey={}", keyWithPrefix, hashKey, e);
            }
            return null;
        }
        if (redisPrintOps) {
            log.info("从redis取得数据并封成对象结束: key={}, hashKey={}, result={}", keyWithPrefix, hashKey, result);
        }
        return result;
    }

    public void hashDelete(String key, String hashKey) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        if (redisPrintOps) {
            log.info("开始删除redis缓存数据: key={}, hashKey={}", keyWithPrefix, hashKey);
        }

        boolean flag = false;
        try {
            flag = hashOperations.get(keyWithPrefix, hashKey) != null;
            if (flag) {
                hashOperations.delete(keyWithPrefix, hashKey);
            }
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("删除redis缓存数据失败: key={}, hashKey={}", keyWithPrefix, hashKey, e);
            }
            return;
        }

        if (redisPrintOps) {
            log.info("删除redis缓存数据结束: key={}, hashKey={}", keyWithPrefix, hashKey);
        }
    }

    public void append(String key, String json) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        if (redisPrintOps) {
            log.info("开始向redis中追加数据: key={}", keyWithPrefix);
        }

        try {
            valueOperations.append(keyWithPrefix, json);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("向redis中追加数据失败: key={}", keyWithPrefix, e);
            }
            return;
        }
        if (redisPrintOps) {
            log.info("向redis中追加数据结束: key={}", keyWithPrefix);
        }
    }

    public void delete(String key) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        if (redisPrintOps) {
            log.info("开始从redis删除数据: key={}", keyWithPrefix);
        }

        boolean flag = false;
        try {
            flag = valueOperations.get(keyWithPrefix) != null;
            if (flag) {
                redisTemplate.delete(keyWithPrefix);
            }
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("从redis删除数据失败: key={}", keyWithPrefix, e);
            }
            return;
        }

        if (redisPrintOps) {
            log.info("从redis删除数据结束: key={}", keyWithPrefix);
        }
    }

    public <T> List<T> getToList(String key, Class<T> clazz) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        if (redisPrintOps) {
            log.info("开始从redis取得数据并封成list返回: key={}", keyWithPrefix);
        }

        List<T> result = null;
        try {
            String resultstr = valueOperations.get(keyWithPrefix);
            if (!StringUtils.hasText(resultstr)) {
                return null;
            }
            result = JsonBinder.buildAlwaysBinder().toJavaListFromJson(resultstr, clazz);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("从redis取得数据失败: key={}", keyWithPrefix, e);
            }
            return null;
        }

        if (redisPrintOps) {
            log.info("开始从redis取得数据并封成对象返回结束: key={}, result={}", keyWithPrefix, result);
        }
        return result;
    }

    public <T> List<T> hashGetToList(String key, String hashKey, Class<T> clazz) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        if (redisPrintOps) {
            log.info("开始从redis取得数据: key={}, hashKey={}", keyWithPrefix, hashKey);
        }

        if (!StringUtils.hasText(key) || !StringUtils.hasText(hashKey)) {
            return null;
        }

        List<T> result = null;
        try {
            Object resultObj = hashOperations.get(keyWithPrefix, hashKey);
            //如果为空,直接返回null
            result = JsonBinder.buildAlwaysBinder().toJavaListFromJson(resultObj + "", clazz);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("从redis取得数据失败: key={}, hashKey={}", keyWithPrefix, hashKey, e);
            }
            return null;
        }

        if (redisPrintOps) {
            log.info("从redis取得数据结束: key={}, hashKey={}", keyWithPrefix, hashKey);
        }
        return result;
    }

    public boolean isExist(String key) {
        String keyWithPrefix = getRedisKeyWithPrefix(key);
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            if (redisPrintOps) {
                log.error("从redis取得数据失败: key={}, hashKey={}", keyWithPrefix, e);
            }
            return false;
        }
    }

    private String getRedisKeyWithPrefix(String key) {
        if (StringUtils.hasText(prefix)) {
            return this.prefix + key;
        } else {
            return key;
        }
    }
}
