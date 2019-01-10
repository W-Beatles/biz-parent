package cn.waynechu.boot.starter.common;

import cn.waynechu.boot.starter.common.properties.CommonProperties;
import cn.waynechu.boot.starter.common.properties.RedisCacheProperties;
import cn.waynechu.boot.starter.common.serializer.FastJsonSerializer;
import cn.waynechu.boot.starter.common.util.RedisCache;
import com.alibaba.fastjson.parser.ParserConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.*;
import org.springframework.util.StringUtils;

import java.time.Duration;

/**
 * @author zhuwei
 * @date 2019/1/10 12:46
 */
@Slf4j
@Configuration
@EnableCaching
@ConditionalOnProperty(value = "common.redis-cache.enable", havingValue = "true")
@EnableConfigurationProperties({CommonProperties.class})
public class RedisCacheAutoConfiguration {

    @Autowired
    private CommonProperties commonProperties;

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory, RedisCacheConfiguration cacheConfiguration) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        return new RedisCacheManager(redisCacheWriter, cacheConfiguration);
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration(RedisSerializer<Object> redisSerializer) {
        return RedisCacheConfiguration.defaultCacheConfig()
                // 设置默认cache超时时间为： 172800秒/2天
                .entryTtl(Duration.ofSeconds(commonProperties.getRedisCache().getTtl()))
                // 设置cache前缀格式为："prefix:cacheName:key"
                .computePrefixWith(cacheName -> commonProperties.getRedisCache().getKeyPrefix() + ":" + cacheName + ":")
                // 设置cache序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer));
    }

    @Bean("defaultRedisSerializer")
    @ConditionalOnMissingBean(RedisSerializer.class)
    public RedisSerializer<Object> redisSerializer() {
        RedisSerializer<Object> redisSerializer;
        // 使用JDK序列化
        if (RedisCacheProperties.SerializerEnum.JDK.equals(commonProperties.getRedisCache().getSerializer())) {
            redisSerializer = new JdkSerializationRedisSerializer();
            log.info("[RedisCache] Using jdkSerializationRedisSerializer for cache");
        }
        // 使用FAST_JSON序列化
        else if (RedisCacheProperties.SerializerEnum.FAST_JSON.equals(commonProperties.getRedisCache().getSerializer())) {
            redisSerializer = new FastJsonSerializer<>(Object.class);
            log.info("[RedisCache] Using fastJsonSerializer for cache");
        }
        // 默认使用JACKSON序列化
        else {
            redisSerializer = new GenericJackson2JsonRedisSerializer();
            log.info("[RedisCache] Missing default redisSerializer, using Jackson2JsonRedisSerializer for cache");
        }
        return redisSerializer;
    }

    /**
     * value为FastJsonRedisSerializer序列化的template
     *
     * @param redisConnectionFactory factory
     * @return template
     */
    @Bean("fastJsonRedisTemplate")
    public RedisTemplate<String, Object> fastJsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 指定AutoType序列化白名单
        ParserConfig.getGlobalInstance().addAccept("cn.waynechu.");

        // 设置key的序列化方式为StringRedisSerializer
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // 设置value的序列化方式为FastJsonRedisSerializer
        FastJsonSerializer<Object> fastJsonRedisSerializer = new FastJsonSerializer<>(Object.class);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisCache redisCache(StringRedisTemplate stringRedisTemplate) {
        if (!StringUtils.hasText(commonProperties.getRedisCache().getKeyPrefix())) {
            log.warn("[RedisCache] Redis key prefix not found, consider setting one");
        }
        return new RedisCache(commonProperties.getRedisCache().getKeyPrefix(), commonProperties.getRedisCache().isPrintOps(), stringRedisTemplate);
    }

    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(factory);
        // 开启事务支持
        stringRedisTemplate.setEnableTransactionSupport(true);
        return stringRedisTemplate;
    }
}
