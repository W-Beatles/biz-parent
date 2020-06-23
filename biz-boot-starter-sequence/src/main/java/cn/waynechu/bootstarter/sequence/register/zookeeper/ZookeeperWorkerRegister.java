package cn.waynechu.bootstarter.sequence.register.zookeeper;

import cn.waynechu.bootstarter.sequence.exception.SequenceException;
import cn.waynechu.bootstarter.sequence.property.SequenceProperty;
import cn.waynechu.bootstarter.sequence.register.WorkerRegister;
import cn.waynechu.bootstarter.sequence.registry.ZookeeperRegistryCenter;
import cn.waynechu.bootstarter.sequence.util.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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

    public ZookeeperWorkerRegister(ZookeeperRegistryCenter registryCenter, SequenceProperty sequenceProperty) {
        this.registryCenter = registryCenter;
        this.nodePath = new NodePath(sequenceProperty.getGroup());
        this.durable = sequenceProperty.isDurable();
        if (StringUtils.isEmpty(sequenceProperty.getRegistryFile())) {
            this.registryFile = getDefaultFilePath(nodePath.getGroupName());
        } else {
            this.registryFile = sequenceProperty.getRegistryFile();
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
            // 有本地缓存的节点信息，且zk也有该节点信息
            if (localNodeInfo != null && children.contains(String.valueOf(localNodeInfo.getWorkerId()))) {
                String key = this.getNodePathKey(nodePath, localNodeInfo.getWorkerId());
                String zkNodeInfoJson = registryCenter.get(key);
                NodeInfo zkNodeInfo = JSON.parseObject(zkNodeInfoJson, NodeInfo.class);
                if (this.checkNodeInfo(localNodeInfo, zkNodeInfo)) {
                    nodePath.setWorkerId(zkNodeInfo.getWorkerId());
                    zkNodeInfo.setUpdateTime(new Date());
                    // 更新ZK节点信息，保存本地缓存，开启定时上报任务
                    this.updateZookeeperNodeInfo(key, zkNodeInfo);
                    this.saveLocalNodeInfo(zkNodeInfo);
                    return zkNodeInfo.getWorkerId();
                }
            }

            // 无本地信息或者缓存数据不匹配，开始向ZK申请节点机器ID
            for (int workerId = 0; workerId < MAX_WORKER_NUM; workerId++) {
                String workerIdStr = String.valueOf(workerId);
                if (!children.contains(workerIdStr)) {
                    // 申请成功
                    NodeInfo applyNodeInfo = initNodeInfo(nodePath.getGroupName(), workerId);
                    nodePath.setWorkerId(applyNodeInfo.getWorkerId());
                    // 保存ZK节点信息，保存本地缓存，开启定时上报任务
                    this.saveZookeeperNodeInfo(nodePath.getWorkerIdPath(), applyNodeInfo);
                    this.saveLocalNodeInfo(applyNodeInfo);
                    return applyNodeInfo.getWorkerId();
                }
            }
            throw new SequenceException("max worker num reached. register failed");
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

    /**
     * 检查节点信息
     *
     * @param localNodeInfo 本地缓存节点信息
     * @param zkNodeInfo    zookeeper节点信息
     * @return true if equals
     */
    private boolean checkNodeInfo(NodeInfo localNodeInfo, NodeInfo zkNodeInfo) {
        try {
            // NodeId、IP、HostName、GroupName 相等（本地缓存==ZK数据）
            if (!zkNodeInfo.getNodeId().equals(localNodeInfo.getNodeId())) {
                return false;
            }
            if (!zkNodeInfo.getIp().equals(localNodeInfo.getIp())) {
                return false;
            }
            if (!zkNodeInfo.getHostName().equals(localNodeInfo.getHostName())) {
                return false;
            }
            if (!zkNodeInfo.getGroupName().equals(localNodeInfo.getGroupName())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("check node info error", e);
            return false;
        }
    }

    /**
     * 刷新ZK节点信息（修改updateTime）
     *
     * @param key      k
     * @param nodeInfo 节点信息
     */
    private void updateZookeeperNodeInfo(String key, NodeInfo nodeInfo) {
        try {
            nodeInfo.setUpdateTime(new Date());
            if (durable) {
                registryCenter.persist(key, jsonizeNodeInfo(nodeInfo));
            } else {
                registryCenter.persistEphemeral(key, jsonizeNodeInfo(nodeInfo));
            }
        } catch (Exception e) {
            log.debug("update zookeeper node info error", e);
        }
    }

    /**
     * 缓存机器节点信息至本地
     *
     * @param nodeInfo 机器节点信息
     */
    private void saveLocalNodeInfo(NodeInfo nodeInfo) {
        try {
            File nodeInfoFile = new File(registryFile);
            String nodeInfoJson = jsonizeNodeInfo(nodeInfo);
            FileUtil.writeStringToFile(nodeInfoFile, nodeInfoJson, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("save node info cache error", e);
        }
    }

    private void saveZookeeperNodeInfo(String key, NodeInfo nodeInfo) {
        if (durable) {
            registryCenter.persist(key, jsonizeNodeInfo(nodeInfo));
        } else {
            registryCenter.persistEphemeral(key, jsonizeNodeInfo(nodeInfo));
        }
    }

    private String jsonizeNodeInfo(NodeInfo nodeInfo) {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        return JSON.toJSONStringWithDateFormat(nodeInfo, dateFormat, SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 初始化节点信息
     *
     * @param groupName 分组名
     * @param workerId  机器号
     * @return 节点信息
     * @throws UnknownHostException e
     */
    private NodeInfo initNodeInfo(String groupName, int workerId) throws UnknownHostException {
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setNodeId(genNodeId());
        nodeInfo.setGroupName(groupName);
        nodeInfo.setWorkerId(workerId);
        nodeInfo.setIp(InetAddress.getLocalHost().getHostAddress());
        nodeInfo.setHostName(InetAddress.getLocalHost().getHostName());
        nodeInfo.setCreateTime(new Date());
        nodeInfo.setUpdateTime(new Date());
        return nodeInfo;
    }

    /**
     * 获取节点唯一ID （基于UUID）
     *
     * @return 节点唯一ID
     */
    private String genNodeId() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    private String getNodePathKey(NodePath nodePath, Integer workerId) {
        return nodePath.getWorkerPath() + "/" + workerId;
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
