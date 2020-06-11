package cn.waynechu.bootstarter.sequence.register.zookeeper;

import cn.waynechu.bootstarter.sequence.register.WorkerRegister;
import cn.waynechu.bootstarter.sequence.registry.RegistryCenter;
import cn.waynechu.bootstarter.sequence.registry.ZookeeperRegistryCenter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

/**
 * @author zhuwei
 * @since 2020/6/11 20:10
 */
public class ZookeeperWorkerRegister implements WorkerRegister {

    private final ZookeeperRegistryCenter registryCenter;

    /**
     * zk节点信息
     */
    private final NodePath nodePath;

    public ZookeeperWorkerRegister(ZookeeperRegistryCenter registryCenter, NodePath nodePath) {
        this.registryCenter = registryCenter;
        this.nodePath = nodePath;
    }

    @Override
    public long register() {
        InterProcessMutex lock = null;
        try {
            CuratorFramework client = (CuratorFramework) registryCenter.getRawClient();
            lock = new InterProcessMutex(client, nodePath.getGroupPath());
            int numOfChildren = registryCenter.getNumChildren(nodePath.getWorkerPath());
        } catch (Exception e) {

        }
        return 0;
    }

    @Override
    public void logout() {

    }
}
