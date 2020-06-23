package cn.waynechu.bootstarter.sequence.registry;

import cn.waynechu.bootstarter.sequence.exception.RegExceptionHandler;
import cn.waynechu.bootstarter.sequence.exception.SequenceErrorCode;
import cn.waynechu.bootstarter.sequence.exception.SequenceException;
import cn.waynechu.bootstarter.sequence.property.ZookeeperProperty;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 基于Zookeeper的注册中心
 *
 * @author zhuwei
 * @since 2020/6/11 20:03
 */
@Slf4j
public class ZookeeperRegistryCenter implements CoordinatorRegistryCenter {

    @Getter
    private CuratorFramework client;

    private ZookeeperProperty zookeeperProperty;

    private final Map<String, CuratorCache> caches = new HashMap<>();

    public ZookeeperRegistryCenter(ZookeeperProperty zookeeperProperty) {
        this.zookeeperProperty = zookeeperProperty;
    }

    @Override
    public void init() {
        log.debug("[sequence]: zookeeper registry center init, server lists is: {}", zookeeperProperty.getServerLists());
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString(zookeeperProperty.getServerLists())
                .retryPolicy(new ExponentialBackoffRetry(zookeeperProperty.getBaseSleepTimeMilliseconds(),
                        zookeeperProperty.getMaxRetries(), zookeeperProperty.getMaxSleepTimeMilliseconds()))
                .namespace(zookeeperProperty.getNamespace());
        if (zookeeperProperty.getSessionTimeoutMilliseconds() != 0) {
            builder.sessionTimeoutMs(zookeeperProperty.getSessionTimeoutMilliseconds());
        }
        if (zookeeperProperty.getConnectionTimeoutMilliseconds() != 0) {
            builder.connectionTimeoutMs(zookeeperProperty.getConnectionTimeoutMilliseconds());
        }
        if (!StringUtils.isEmpty(zookeeperProperty.getDigest())) {
            builder.authorization("digest", zookeeperProperty.getDigest().getBytes(StandardCharsets.UTF_8))
                    .aclProvider(new ACLProvider() {

                        @Override
                        public List<ACL> getDefaultAcl() {
                            return ZooDefs.Ids.CREATOR_ALL_ACL;
                        }

                        @Override
                        public List<ACL> getAclForPath(final String path) {
                            return ZooDefs.Ids.CREATOR_ALL_ACL;
                        }
                    });
        }
        client = builder.build();
        client.start();
        try {
            if (!client.blockUntilConnected(zookeeperProperty.getMaxSleepTimeMilliseconds() * zookeeperProperty.getMaxRetries(), TimeUnit.MILLISECONDS)) {
                client.close();
                throw new KeeperException.OperationTimeoutException();
            }
        } catch (Exception e) {
            throw new SequenceException(SequenceErrorCode.SEQUENCE_DEFAULT_ERROR_CODE, e.getMessage());
        }
    }

    @Override
    public void close() {
        for (Map.Entry<String, CuratorCache> each : caches.entrySet()) {
            each.getValue().close();
        }
        waitForCacheClose();
        CloseableUtils.closeQuietly(client);
    }

    /**
     * TODO 等待500ms, cache先关闭再关闭client, 否则会抛异常
     * 因为异步处理, 可能会导致client先关闭而cache还未关闭结束.
     * 等待Curator新版本解决这个bug.
     * BUG地址：https://issues.apache.org/jira/browse/CURATOR-157
     */
    private void waitForCacheClose() {
        try {
            Thread.sleep(500L);
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String get(String key) {
        CuratorCache cache = findTreeCache(key);
        if (cache == null) {
            return getDirectly(key);
        }
        Optional<ChildData> childDataOptional = cache.get(key);
        return childDataOptional.map(childData -> new String(childData.getData(), Charsets.UTF_8))
                .orElseGet(() -> getDirectly(key));
    }

    private CuratorCache findTreeCache(final String key) {
        for (Map.Entry<String, CuratorCache> entry : caches.entrySet()) {
            if (key.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public String getDirectly(String key) {
        try {
            return new String(client.getData().forPath(key), Charsets.UTF_8);
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
            return null;
        }
    }

    @Override
    public List<String> getChildrenKeys(String key) {
        try {
            List<String> result = client.getChildren().forPath(key);
            result.sort(Comparator.reverseOrder());
            return result;
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
            return Collections.emptyList();
        }
    }

    @Override
    public int getNumChildren(String key) {
        try {
            Stat stat = client.checkExists().forPath(key);
            if (stat != null) {
                return stat.getNumChildren();
            }
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
        }
        return 0;
    }

    @Override
    public boolean isExisted(String key) {
        try {
            return client.checkExists().forPath(key) != null;
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
            return false;
        }
    }

    @Override
    public void persist(String key, String value) {
        try {
            if (!isExisted(key)) {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                        .forPath(key, value.getBytes(StandardCharsets.UTF_8));
            } else {
                update(key, value);
            }
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
        }
    }

    @Override
    public void update(String key, String value) {
        try {
            client.inTransaction().check().forPath(key)
                    .and().setData().forPath(key, value.getBytes(Charsets.UTF_8))
                    .and().commit();
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
        }
    }

    @Override
    public void persistEphemeral(String key, String value) {
        try {
            if (isExisted(key)) {
                client.delete().deletingChildrenIfNeeded().forPath(key);
            }
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(key, value.getBytes(Charsets.UTF_8));
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
        }
    }

    @Override
    public String persistSequential(String key, String value) {
        try {
            return client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(key, value.getBytes(Charsets.UTF_8));
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
        }
        return null;
    }

    @Override
    public void persistEphemeralSequential(String key) {
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(key);
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
        }
    }

    @Override
    public void remove(String key) {
        try {
            client.delete().deletingChildrenIfNeeded().forPath(key);
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
        }
    }

    @Override
    public long getRegistryCenterTime(String key) {
        long result = 0L;
        try {
            persist(key, "");
            result = client.checkExists().forPath(key).getMtime();
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
        }
        Preconditions.checkState(0L != result, "Cannot get registry center time.");
        return result;
    }

    @Override
    public Object getRawClient() {
        return client;
    }

    @Override
    public void addCacheData(String cachePath) {
        CuratorCache cache = CuratorCache.build(client, cachePath);
        try {
            cache.start();
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
        }
        caches.put(cachePath + "/", cache);
    }

    @Override
    public void evictCacheData(String cachePath) {
        CuratorCache cache = caches.remove(cachePath + "/");
        if (null != cache) {
            cache.close();
        }
    }

    @Override
    public Object getRawCache(String cachePath) {
        return caches.get(cachePath + "/");
    }
}
