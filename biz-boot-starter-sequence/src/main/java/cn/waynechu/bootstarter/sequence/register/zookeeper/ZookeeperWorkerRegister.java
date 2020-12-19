package cn.waynechu.bootstarter.sequence.register.zookeeper;

import cn.waynechu.bootstarter.sequence.exception.SequenceException;
import cn.waynechu.bootstarter.sequence.property.SequenceProperty;
import cn.waynechu.bootstarter.sequence.register.WorkerRegister;
import cn.waynechu.bootstarter.sequence.registry.ZookeeperRegistryCenter;
import cn.waynechu.bootstarter.sequence.util.FileUtil;
import cn.waynechu.bootstarter.sequence.util.TagPathUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author zhuwei
 * @since 2020/6/11 20:10
 */
@Slf4j
public class ZookeeperWorkerRegister implements WorkerRegister {

    private static final long MAX_WORKER_NUM = 1024;

    private static final int MAX_LOCK_WAIT_TIME_MS = 30 * 1000;

    private String registryFilePath;

    private ScheduledExecutorService senderPool;

    private ZookeeperRegistryCenter registryCenter;

    public ZookeeperWorkerRegister(ZookeeperRegistryCenter registryCenter, SequenceProperty sequenceProperty) {
        this.registryFilePath = sequenceProperty.getRegistryFile();
        this.registryCenter = registryCenter;
    }

    @Override
    public void init() {
        this.senderPool = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r, "SequenceScheduleThread");
            thread.setDaemon(true);
            return thread;
        });
    }

    @Override
    public int register(String bizTag) {
        InterProcessMutex lock = null;
        try {
            CuratorFramework client = (CuratorFramework) registryCenter.getRawClient();
            String tagPath = TagPathUtil.getTagPath(bizTag);
            lock = new InterProcessMutex(client, tagPath);
            int numOfChildren = registryCenter.getNumChildren(tagPath);
            if (numOfChildren > MAX_WORKER_NUM) {
                String message = String.format("Tag: %s reach the maximum number of worker, register failed", bizTag);
                throw new SequenceException(message);
            }

            if (!lock.acquire(MAX_LOCK_WAIT_TIME_MS, TimeUnit.MILLISECONDS)) {
                String message = String.format("Acquire lock failed after %s ms", MAX_LOCK_WAIT_TIME_MS);
                throw new TimeoutException(message);
            }

            NodeInfo localNodeInfo = this.getLocalNodeInfo(bizTag);
            List<String> children = registryCenter.getChildrenKeys(tagPath);
            // 本地缓存与zk节点信息一致
            if (localNodeInfo != null && children.contains(String.valueOf(localNodeInfo.getWorkerId()))) {
                String workerPath = TagPathUtil.getWorkerPath(bizTag, localNodeInfo.getWorkerId());
                String workInfoJson = registryCenter.get(workerPath);
                NodeInfo workInfo = JSON.parseObject(workInfoJson, NodeInfo.class);
                if (this.checkNodeInfo(localNodeInfo, workInfo)) {
                    workInfo.setUpdateTime(LocalDateTime.now());
                    // 更新ZK节点信息，保存本地缓存，开启定时上报任务
                    this.updateZookeeperNodeInfo(workerPath, workInfo);
                    this.saveLocalNodeInfo(bizTag, workInfo);
                    this.scheduleUploadNodeInfo(workerPath, workInfo);
                    return workInfo.getWorkerId();
                }
            }

            // 无本地信息或者缓存数据不匹配，重新申请节点ID
            for (int workerId = 1; workerId <= MAX_WORKER_NUM; workerId++) {
                if (!children.contains(String.valueOf(workerId))) {
                    // 申请成功
                    NodeInfo workInfo = this.initNodeInfo(bizTag, workerId);
                    // 保存ZK节点信息，保存本地缓存，开启定时上报任务
                    String workerPath = TagPathUtil.getWorkerPath(bizTag, workerId);
                    this.saveZookeeperNodeInfo(workerPath, workInfo);
                    this.saveLocalNodeInfo(bizTag, workInfo);
                    this.scheduleUploadNodeInfo(workerPath, workInfo);
                    return workInfo.getWorkerId();
                }
            }
            throw new SequenceException("Max worker num reached, register failed");
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
    public void logout(String bizTag, Integer workerId) {
        CuratorFramework client = (CuratorFramework) registryCenter.getRawClient();
        if (client != null && client.getState() == CuratorFrameworkState.STARTED) {
            // 移除注册节点
            registryCenter.remove(TagPathUtil.getWorkerPath(bizTag, workerId));
        }
    }

    @Override
    public void close() {
        // 关闭连接
        registryCenter.close();
    }

    /**
     * 检查节点信息
     *
     * @param localNodeInfo 本地缓存节点信息
     * @param zkNodeInfo    zookeeper节点信息
     * @return true if equals
     */
    private boolean checkNodeInfo(NodeInfo localNodeInfo, NodeInfo zkNodeInfo) {
        try {
            if (!zkNodeInfo.getNodeId().equals(localNodeInfo.getNodeId())) {
                return false;
            }
            if (!zkNodeInfo.getBizTag().equals(localNodeInfo.getBizTag())) {
                return false;
            }
            if (!zkNodeInfo.getWorkerId().equals(localNodeInfo.getWorkerId())) {
                return false;
            }
            if (!zkNodeInfo.getIp().equals(localNodeInfo.getIp())) {
                return false;
            }
            return zkNodeInfo.getHostName().equals(localNodeInfo.getHostName());
        } catch (Exception e) {
            log.error("Check node info error", e);
            return false;
        }
    }

    /**
     * 缓存机器节点信息至本地
     *
     * @param nodeInfo 机器节点信息
     */
    private void saveLocalNodeInfo(String bizTag, NodeInfo nodeInfo) {
        try {
            File nodeInfoFile = new File(registryFilePath + File.separator + bizTag + ".cache");
            String nodeInfoJson = JSONObject.toJSONString(nodeInfo);
            FileUtil.writeStringToFile(nodeInfoFile, nodeInfoJson, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Save node info cache error", e);
        }
    }

    /**
     * 读取本地缓存机器节点
     *
     * @return 机器节点信息
     */
    private NodeInfo getLocalNodeInfo(String bizTag) {
        try {
            File nodeInfoFile = new File(registryFilePath + File.separator + bizTag + ".cache");
            if (nodeInfoFile.exists()) {
                String nodeInfoJson = FileUtil.readFileToString(nodeInfoFile, StandardCharsets.UTF_8);
                return JSON.parseObject(nodeInfoJson, NodeInfo.class);
            }
        } catch (Exception e) {
            log.error("Read node info cache error", e);
        }
        return null;
    }

    /**
     * 保存zk节点信息
     *
     * @param key      k
     * @param nodeInfo 节点信息
     */
    private void saveZookeeperNodeInfo(String key, NodeInfo nodeInfo) {
        String nodeInfoJson = JSONObject.toJSONString(nodeInfo);
        try {
            registryCenter.persistEphemeral(key, nodeInfoJson);
        } catch (Exception e) {
            log.error("Update zookeeper node info error", e);
        }
    }

    /**
     * 刷新ZK节点信息
     *
     * @param key      k
     * @param nodeInfo 节点信息
     */
    private void updateZookeeperNodeInfo(String key, NodeInfo nodeInfo) {
        nodeInfo.setUpdateTime(LocalDateTime.now());
        this.saveZookeeperNodeInfo(key, nodeInfo);
    }

    /**
     * 更新节点信息
     *
     * @param key      k
     * @param nodeInfo 节点信息
     */
    private void scheduleUploadNodeInfo(final String key, final NodeInfo nodeInfo) {
        senderPool.scheduleAtFixedRate(() -> updateZookeeperNodeInfo(key, nodeInfo),
                3L, 3L, TimeUnit.SECONDS);
    }

    /**
     * 初始化节点信息
     *
     * @param bizTag   业务标识
     * @param workerId 机器号
     * @return 节点信息
     * @throws UnknownHostException e
     */
    private NodeInfo initNodeInfo(String bizTag, int workerId) throws UnknownHostException {
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setNodeId(genNodeId());
        nodeInfo.setBizTag(bizTag);
        nodeInfo.setWorkerId(workerId);
        nodeInfo.setIp(InetAddress.getLocalHost().getHostAddress());
        nodeInfo.setHostName(InetAddress.getLocalHost().getHostName());
        nodeInfo.setCreateTime(LocalDateTime.now());
        nodeInfo.setUpdateTime(LocalDateTime.now());
        return nodeInfo;
    }

    /**
     * 获取节点唯一ID
     *
     * @return 节点唯一ID
     */
    private String genNodeId() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
