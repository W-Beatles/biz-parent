package cn.waynechu.springcloud.test.cache;

import org.springframework.cache.support.AbstractCacheManager;

import java.util.Collection;

/**
 * 自定义CacheManager
 *
 * @author zhuwei
 * @since 2019/1/25 14:33
 */
public class MyCacheManager extends AbstractCacheManager {

    private Collection<? extends MyCache> caches;

    public void setCaches(Collection<? extends MyCache> caches) {
        this.caches = caches;
    }

    @Override
    protected Collection<? extends MyCache> loadCaches() {
        return this.caches;
    }
}
