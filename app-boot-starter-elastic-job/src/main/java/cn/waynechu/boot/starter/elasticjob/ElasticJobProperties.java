package cn.waynechu.boot.starter.elasticjob;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhuwei
 * @date 2019/2/25 16:54
 */
@Data
@ConfigurationProperties(prefix = "elastic.job")
public class ElasticJobProperties {

    /**
     * 是否开启ElasticJob。默认关闭
     */
    private boolean enable = false;

    /**
     * 连接Zookeeper服务器的列表
     * 包括IP地址和端口号
     * 多个地址用逗号分隔
     * 如: host1:2181,host2:2181
     */
    private String serverLists;

    /**
     * Zookeeper的命名空间
     */
    private String namespace = "elastic-job/app";

    /**
     * 追踪日志配置
     */
    private JobLogDataSourceProperties logds;

    /**
     * 是否记录定时任务运行日志
     */
    private boolean log;

    @Data
    public static class JobLogDataSourceProperties {
        /**
         * 数据库连接url
         */
        private String url;

        /**
         * 数据库用户名
         */
        private String username;

        /**
         * 数据库密码
         */
        private String password;

        /**
         * 用来解密的密码公钥
         */
        private String pwdPublicKey;
    }
}
