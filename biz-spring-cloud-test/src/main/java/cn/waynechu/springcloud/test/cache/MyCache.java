package cn.waynechu.springcloud.test.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简易的自定义Cache
 * <p>
 * 使用ConcurrentHashMap存储缓存数据，更多详情可以参考 {@code ConcurrentMapCache}
 *
 * @author zhuwei
 * @date 2019/1/25 14:26
 */
@Slf4j
public class MyCache implements Cache {

    private String name;

    private Map<Object, Object> store = new ConcurrentHashMap<>();

    public MyCache(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return store;
    }

    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper result = null;
        Object value = store.get(key);
        if (value != null) {
            log.info("[{}] got cache, key: {}", name, key);
            result = new SimpleValueWrapper(value);
        } else {
            log.info("[{}] missing cache, key: {}", name, key);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Class<T> type) {
        ValueWrapper wrapper = get(key);
        if (wrapper == null) {
            return null;
        }
        return (T) wrapper.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        ValueWrapper wrapper = get(key);
        if (wrapper == null) {
            return null;
        }
        return (T) wrapper.get();
    }

    @Override
    public void put(Object key, Object value) {
        if (value != null) {
            store.put(key, value);
        }
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        Object existing = store.putIfAbsent(key, value);
        return existing != null ? new SimpleValueWrapper(existing) : null;
    }

    @Override
    public void evict(Object key) {
        store.remove(key);
    }

    @Override
    public void clear() {
        store.clear();
    }
}
