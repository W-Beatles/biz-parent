package cn.waynechu.boot.starter.common.properties;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuwei
 * @date 2019/1/4 10:14
 */
@Data
public class RedisCacheProperties {

    /**
     * 是否开启RedisCache。默认false
     */
    private boolean enable = false;

    /**
     * RedisCache存储前缀
     */
    private String keyPrefix = "";

    /**
     * 打印Redis操作详情。默认false
     */
    private boolean printOps = false;

    /**
     * Cache序列化方式。默认JACKSON
     */
    private SerializerEnum serializer = SerializerEnum.JACKSON;

    /**
     * 当序列化方式为FastJSON时，需指定autoType白名单，否则反序列化时会抛出异常
     */
    private List<String> autoTypes = new ArrayList<>();

    /**
     * 配置全局Cache超时时间，单位秒。默认 86400秒/1天
     */
    private long ttl = 86400;

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
