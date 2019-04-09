package cn.waynechu.springcloud.apicommon.properties;

import lombok.Data;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Cache序列化方式。默认JACKSON
     */
    private SerializerEnum serializer = SerializerEnum.JACKSON;

    /**
     * 当序列化方式为FastJSON时，需指定autoType白名单，否则反序列化时会抛出异常
     */
    private List<String> autoTypes = new ArrayList<>();

    /**
     * 配置全局Cache超时时间。默认1天
     */
    private Duration globalTtl = Duration.ofDays(1);

    /**
     * 单独配置指定Cache的超时时间。key: cacheName, value: ttl
     */
    private Map<String, Duration> customTtl = new HashMap<>();

    /**
     * 打印Redis操作详情。默认false
     */
    private boolean printOps = false;

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
