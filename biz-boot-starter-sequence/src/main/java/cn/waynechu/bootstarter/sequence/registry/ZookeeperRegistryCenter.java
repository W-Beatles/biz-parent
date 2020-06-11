package cn.waynechu.bootstarter.sequence.registry;

import cn.waynechu.bootstarter.sequence.exception.RegExceptionHandler;
import cn.waynechu.bootstarter.sequence.exception.SequenceErrorCode;
import cn.waynechu.bootstarter.sequence.exception.SequenceException;
import cn.waynechu.bootstarter.sequence.property.SequenceProperty;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 基于Zookeeper的注册中心
 *
 * @author zhuwei
 * @since 2020/6/11 20:03
 */
@Slf4j
public class ZookeeperRegistryCenter implements CoordinatorRegistryCenter {

    private CuratorFramework client;

    private SequenceProperty sequenceProperty;

    private final Map<String, CuratorCache> caches = new HashMap<>();

    public ZookeeperRegistryCenter(SequenceProperty sequenceProperty) {
        this.sequenceProperty = sequenceProperty;
    }

    @Override
    public void init() {
        if (client != null) {
            return;
        }

        log.debug("init zookeeper connector, connect to servers : {}", sequenceProperty.getServerLists());
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString(sequenceProperty.getServerLists())
                .retryPolicy(new ExponentialBackoffRetry(sequenceProperty.getBaseSleepTimeMilliseconds(),
                        sequenceProperty.getMaxRetries(), sequenceProperty.getMaxSleepTimeMilliseconds()))
                .namespace(sequenceProperty.getNamespace());
        if (sequenceProperty.getSessionTimeoutMilliseconds() != 0) {
            builder.sessionTimeoutMs(sequenceProperty.getSessionTimeoutMilliseconds());
        }
        if (sequenceProperty.getConnectionTimeoutMilliseconds() != 0) {
            builder.connectionTimeoutMs(sequenceProperty.getConnectionTimeoutMilliseconds());
        }
        if (!StringUtils.isEmpty(sequenceProperty.getDigest())) {
            builder.authorization("digest", sequenceProperty.getDigest().getBytes(StandardCharsets.UTF_8))
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
            if (!client.blockUntilConnected(sequenceProperty.getMaxSleepTimeMilliseconds() * sequenceProperty.getMaxRetries(), TimeUnit.MILLISECONDS)) {
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
        CloseableUtils.closeQuietly(client);
    }

    @Override
    public String get(String key) {
        CuratorCache cache = findTreeCache(key);
        if (null == cache) {
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
    public boolean isExisted(String key) {
        return false;
    }

    @Override
    public void persist(String key, String value) {

    }

    @Override
    public void update(String key, String value) {

    }

    @Override
    public void remove(String key) {

    }

    @Override
    public long getRegistryCenterTime(String key) {
        return 0;
    }

    @Override
    public Object getRawClient() {
        return null;
    }

    @Override
    public String getDirectly(String key) {
        try {
            return new String(client.getData().forPath(key), Charsets.UTF_8);
        } catch (Exception ex) {
            RegExceptionHandler.handleException(ex);
            return null;
        }
    }

    @Override
    public List<String> getChildrenKeys(String key) {
        return null;
    }

    @Override
    public int getNumChildren(String key) {
        return 0;
    }

    @Override
    public void persistEphemeral(String key, String value) {

    }

    @Override
    public String persistSequential(String key, String value) {
        return null;
    }

    @Override
    public void persistEphemeralSequential(String key) {

    }

    @Override
    public void addCacheData(String cachePath) {

    }

    @Override
    public void evictCacheData(String cachePath) {

    }

    @Override
    public Object getRawCache(String cachePath) {
        return null;
    }
}
