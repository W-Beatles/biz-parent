package cn.waynechu.springcloud.apicommon.config;

import cn.waynechu.springcloud.apicommon.properties.RedisCacheProperty;
import cn.waynechu.springcloud.apicommon.cache.RedisCache;
import cn.waynechu.springcloud.common.serializer.FastJsonSerializer;
import com.alibaba.fastjson.parser.ParserConfig;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuwei
 * @date 2019/1/10 12:46
 */
@Slf4j
@Deprecated
//@Configuration
@EnableCaching
@ConditionalOnProperty(value = RedisCacheProperty.REDIS_CACHE_CONFIG_PREFIX + ".enable", havingValue = "true")
@EnableConfigurationProperties({RedisCacheProperty.class})
public class RedisCacheAutoConfiguration {

    @Autowired
    private RedisCacheProperty redisCacheProperty;

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);

        // 全局RedisCache配置
        RedisCacheConfiguration globalCacheConfiguration = createCacheConfig(redisSerializer(),
                redisCacheProperty.getGlobalTtl(), redisCacheProperty.getKeyPrefix());

        // 用户自定义缓存配置
        Map<String, Duration> customCacheTtlMap = redisCacheProperty.getCustomTtl();
        Map<String, RedisCacheConfiguration> cacheConfigurationMap = new HashMap<>(customCacheTtlMap.size());
        customCacheTtlMap.forEach((cacheName, ttl) -> cacheConfigurationMap.put(cacheName,
                createCacheConfig(redisSerializer(), ttl, redisCacheProperty.getKeyPrefix())));
        return new RedisCacheManager(redisCacheWriter, globalCacheConfiguration, cacheConfigurationMap);
    }

    @Bean("defaultRedisSerializer")
    @ConditionalOnMissingBean(RedisSerializer.class)
    public RedisSerializer<Object> redisSerializer() {
        RedisSerializer<Object> redisSerializer;
        if (RedisCacheProperty.SerializerEnum.JDK.equals(redisCacheProperty.getSerializer())) {
            redisSerializer = new JdkSerializationRedisSerializer();
            log.info("[RedisCache] Using JdkSerializationRedisSerializer.class for redis cache");
        } else if (RedisCacheProperty.SerializerEnum.FAST_JSON.equals(redisCacheProperty.getSerializer())) {
            redisSerializer = new FastJsonSerializer<>(Object.class);
            log.info("[RedisCache] Using FastJsonSerializer.class for redis cache");

            // FastJson需指定AutoType序列化白名单
            for (String autoType : redisCacheProperty.getAutoTypes()) {
                ParserConfig.getGlobalInstance().addAccept(autoType);
            }
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            // 序列化时使用 @type 字段存储非 final 类型对象的类信息
            TypeResolverBuilder<?> typer = new ObjectMapper.DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping.NON_FINAL)
                    .init(JsonTypeInfo.Id.CLASS, null)
                    .inclusion(JsonTypeInfo.As.PROPERTY)
                    .typeProperty("@type");
            objectMapper.setDefaultTyping(typer);
            // 设置只序列化非空字段
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            // 设置反序列化时存在未知属性不抛出异常
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            redisSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
            log.info("[RedisCache] Using Jackson2JsonRedisSerializer.class for redis cache");
        }
        return redisSerializer;
    }

    @Bean("defaultRedisTemplate")
    public RedisTemplate<String, Object> fastJsonRedisTemplate(RedisConnectionFactory redisConnectionFactory, RedisSerializer redisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 设置key的序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // 设置value的序列化方式
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);

        // 开启事务支持
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisCache redisCache(RedisTemplate<String, Object> redisTemplate) {
        if (!StringUtils.hasText(redisCacheProperty.getKeyPrefix())) {
            log.warn("[RedisCache] Redis key prefix not found, consider setting one");
        }
        return new RedisCache(redisCacheProperty.getKeyPrefix(), redisCacheProperty.isPrintOps(), redisTemplate);
    }

    /**
     * 生成RedisCache配置
     *
     * @param redisSerializer 序列化策略
     * @param entryTtl        过期时间
     * @param cachePrefix     key前缀
     * @return RedisCacheConfiguration
     */
    private static RedisCacheConfiguration createCacheConfig(RedisSerializer<Object> redisSerializer,
                                                             Duration entryTtl, String cachePrefix) {
        return RedisCacheConfiguration.defaultCacheConfig()
                // 设置默认cache超时时间为：86400秒/1天
                .entryTtl(entryTtl)
                // 设置cache前缀格式为："prefix:cacheName:key"
                .computePrefixWith(cacheName -> cachePrefix + ":" + cacheName + ":")
                // 设置cache-value序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer));
    }
}
