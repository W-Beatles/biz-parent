package cn.waynechu.app.boot.starter.common.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author zhuwei
 * @date 2018/12/28 16:09
 */
@Slf4j
public class RedisCache {
    private String prefix;
    private StringRedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOperations;

    public RedisCache(String prefix, StringRedisTemplate redisTemplate) {
        super();
        this.prefix = prefix;
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }

    public boolean set(String key, Object obj, int seconds) {
        try {
            String objectJson = JSONObject.toJSONString(obj);
            valueOperations.set(getRedisKey(key), objectJson, seconds, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.error("缓存异常", e);
            return false;
        }
    }

    public <T> T get(String key, Class<T> clazz) {
        try {
            String value = valueOperations.get(getRedisKey(key));
            return JSONObject.parseObject(value, clazz);
        } catch (Exception e) {
            log.error("缓存异常", e);
            return null;
        }
    }

    public boolean remove(String key) {
        try {
            redisTemplate.delete(getRedisKey(key));
            return true;
        } catch (Exception e) {
            log.error("缓存异常", e);
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
