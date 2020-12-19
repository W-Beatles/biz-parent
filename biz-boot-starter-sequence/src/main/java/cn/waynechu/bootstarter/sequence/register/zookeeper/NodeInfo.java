package cn.waynechu.bootstarter.sequence.register.zookeeper;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhuwei
 * @since 2020/6/23 10:55
 */
@Data
public class NodeInfo {

    private String nodeId;

    private String bizTag;

    private Integer workerId;

    private String ip;

    private String hostName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
