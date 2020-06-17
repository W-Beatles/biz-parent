package cn.waynechu.bootstarter.sequence.property;

import lombok.Data;

/**
 * @author zhuwei
 * @since 2020/6/17 16:36
 */
@Data
public class ApplicationConfiguration {

    /**
     * 分组名字，默认default
     */
    private String group = "default";

    /**
     * 生成策略，默认snowflake
     */
    private String strategy = "snowflake";

    /**
     * zk节点信息本地缓存文件路径
     */
    private String registryFile;

    /**
     * zk节点是否持久化存储
     */
    private boolean durable;
}
