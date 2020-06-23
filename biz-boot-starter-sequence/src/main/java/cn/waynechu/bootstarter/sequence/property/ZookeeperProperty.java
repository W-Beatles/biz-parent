package cn.waynechu.bootstarter.sequence.property;

import lombok.Data;

/**
 * @author zhuwei
 * @since 2020/6/11 16:01
 */
@Data
public class ZookeeperProperty {

    /**
     * 连接Zookeeper服务器的列表
     * <p>
     * 包括IP地址和端口号，多个地址用逗号分隔，如: host1:2181,host2:2181
     */
    private String serverLists;

    /**
     * 命名空间
     */
    private String namespace = "sequence";

    /**
     * 等待重试的间隔时间的初始值
     * <p>
     * 单位毫秒，默认1s
     */
    private int baseSleepTimeMilliseconds = 1000;

    /**
     * 等待重试的间隔时间的最大值
     * <p>
     * 单位毫秒，默认3s
     */
    private int maxSleepTimeMilliseconds = 3000;

    /**
     * 最大重试次数
     */
    private int maxRetries = 3;

    /**
     * 会话超时时间
     * <p>
     * 单位毫秒，默认60s
     */
    private int sessionTimeoutMilliseconds;

    /**
     * 连接超时时间
     * <p>
     * 单位毫秒，默认15s
     */
    private int connectionTimeoutMilliseconds;

    /**
     * 连接Zookeeper的权限令牌
     * <p>
     * 缺省为不需要权限验证
     */
    private String digest;
}
