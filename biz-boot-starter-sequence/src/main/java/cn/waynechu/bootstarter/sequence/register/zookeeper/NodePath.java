package cn.waynechu.bootstarter.sequence.register.zookeeper;

import lombok.Data;

/**
 * @author zhuwei
 * @since 2020/6/11 20:15
 */
@Data
public class NodePath {

    private static final String WORKER_NODE = "worker";

    /**
     * workerId 分组
     */
    private String groupName;

    /**
     * 机器编号
     */
    private long workerId;

    public NodePath(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupPath() {
        return String.format("/%s", groupName);
    }

    public String getWorkerPath() {
        return String.format("/%s/%s", groupName, WORKER_NODE);
    }

    public String getWorkerIdPath() {
        return String.format("/%s/%s/%s", groupName, WORKER_NODE, workerId);
    }
}
