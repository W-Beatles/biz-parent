package cn.waynechu.boot.starter.common.properties;

import lombok.Data;

/**
 * @author zhuwei
 * @date 2019/1/4 10:14
 */
@Data
public class RedisCacheProperties {

    /**
     * 是否开启RedisCache
     */
    private boolean enable = false;

    /**
     * RedisCache存储前缀
     */
    private String keyPrefix = "";

    /**
     * 打印Redis操作详情
     */
    private boolean printOps = false;

    /**
     * Cache序列化方式，默认JACKSON
     */
    private SerializerEnum serializer = SerializerEnum.JACKSON;

    /**
     * 配置全局Cache超时时间，单位秒。默认 172800秒/2天
     */
    private long ttl = 172800;

    public enum SerializerEnum {
        /**
         * 使用JdkSerializationRedisSerializer进行序列化
         */
        JDK,
        /**
         * 使用Jackson2JsonRedisSerializer进行序列化
         */
        JACKSON,
        /**
         * 使用FastJsonSerializer进行序列化
         */
        FAST_JSON
    }
}
