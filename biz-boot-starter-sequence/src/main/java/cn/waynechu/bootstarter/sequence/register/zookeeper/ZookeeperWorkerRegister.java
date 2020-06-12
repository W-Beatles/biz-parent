package cn.waynechu.bootstarter.sequence.register.zookeeper;

import cn.waynechu.bootstarter.sequence.exception.SequenceException;
import cn.waynechu.bootstarter.sequence.register.WorkerRegister;
import cn.waynechu.bootstarter.sequence.registry.ZookeeperRegistryCenter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author zhuwei
 * @since 2020/6/11 20:10
 */
@Slf4j
public class ZookeeperWorkerRegister implements WorkerRegister {

    /**
     * 最大机器数
     */
    private static final long MAX_WORKER_NUM = 1024;

    /**
     * 加锁最大等待时间
     */
    private static final int MAX_LOCK_WAIT_TIME_MS = 30 * 1000;

    private final ZookeeperRegistryCenter registryCenter;

    /**
     * zk节点信息
     */
    private NodePath nodePath;

    public ZookeeperWorkerRegister(ZookeeperRegistryCenter registryCenter) {
        this.registryCenter = registryCenter;
    }

    @Override
    public long register() {
        InterProcessMutex lock = null;
        try {
            CuratorFramework client = (CuratorFramework) registryCenter.getRawClient();
            lock = new InterProcessMutex(client, nodePath.getGroupPath());
            int numOfChildren = registryCenter.getNumChildren(nodePath.getWorkerPath());
            if (numOfChildren > MAX_WORKER_NUM) {
                throw new SequenceException("max worker num reached. register failed");
            }

            if (!lock.acquire(MAX_LOCK_WAIT_TIME_MS, TimeUnit.MILLISECONDS)) {
                String message = String.format("acquire lock failed after %s ms.", MAX_LOCK_WAIT_TIME_MS);
                throw new TimeoutException(message);
            }
            return 0;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            try {
                if (lock != null) {
                    lock.release();
                }
            } catch (Exception e) {
                // do nothing here.
            }
        }
    }

    @Override
    public void logout() {
        CuratorFramework client = (CuratorFramework) registryCenter.getRawClient();
        if (client != null && client.getState() == CuratorFrameworkState.STARTED) {
            // 移除注册节点
            registryCenter.remove(nodePath.getWorkerIdPath());
            // 关闭连接
            registryCenter.close();
        }
    }
}
