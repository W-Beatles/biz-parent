package cn.waynechu.bootstarter.sequence.register.zookeeper;

import cn.waynechu.bootstarter.sequence.exception.SequenceException;
import cn.waynechu.bootstarter.sequence.property.ApplicationConfiguration;
import cn.waynechu.bootstarter.sequence.register.WorkerRegister;
import cn.waynechu.bootstarter.sequence.registry.ZookeeperRegistryCenter;
import cn.waynechu.bootstarter.sequence.util.FileUtil;
import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xalan.internal.lib.NodeInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
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
     * 注册文件
     */
    private String registryFile;

    /**
     * zk节点信息
     */
    private NodePath nodePath;

    /**
     * zk节点是否持久化存储
     */
    private boolean durable;

    public ZookeeperWorkerRegister(ZookeeperRegistryCenter registryCenter, ApplicationConfiguration applicationConfiguration) {
        this.registryCenter = registryCenter;
        this.nodePath = new NodePath(applicationConfiguration.getGroup());
        this.durable = applicationConfiguration.isDurable();
        if (StringUtils.isEmpty(applicationConfiguration.getRegistryFile())) {
            this.registryFile = getDefaultFilePath(nodePath.getGroupName());
        } else {
            this.registryFile = applicationConfiguration.getRegistryFile();
        }
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

            NodeInfo localNodeInfo = this.getLocalNodeInfo();
            List<String> children = registryCenter.getChildrenKeys(nodePath.getWorkerPath());
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

    /**
     * 获取本地节点缓存文件路径
     *
     * @param groupName 分组名
     * @return 文件路径
     */
    private String getDefaultFilePath(String groupName) {
        return "." + File.separator + "tmp" +
                File.separator + "sequence" +
                File.separator + groupName + ".cache";
    }

    /**
     * 读取本地缓存机器节点
     *
     * @return 机器节点信息
     */
    private NodeInfo getLocalNodeInfo() {
        try {
            File nodeInfoFile = new File(registryFile);
            if (nodeInfoFile.exists()) {

                String nodeInfoJson = FileUtil.readFileToString(nodeInfoFile, StandardCharsets.UTF_8);
                return this.createNodeInfoFromJsonStr(nodeInfoJson);
            }
        } catch (Exception e) {
            log.error("read node info cache error", e);
        }
        return null;
    }

    /**
     * 通过节点信息JSON字符串反序列化节点信息
     *
     * @param jsonStr 节点信息JSON字符串
     * @return 节点信息
     */
    private NodeInfo createNodeInfoFromJsonStr(String jsonStr) {
        return JSON.parseObject(jsonStr, NodeInfo.class);
    }
}
