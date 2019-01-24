package cn.waynechu.boot.starter.common;

import cn.waynechu.boot.starter.common.properties.CommonProperties;
import cn.waynechu.boot.starter.common.properties.RedisCacheProperties;
import cn.waynechu.boot.starter.common.serializer.FastJsonSerializer;
import cn.waynechu.boot.starter.common.util.RedisCache;
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
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
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
                // 设置默认cache超时时间为：86400秒/1天
                .entryTtl(Duration.ofSeconds(commonProperties.getRedisCache().getTtl()))
                // 设置cache前缀格式为："prefix:cacheName:key"
                .computePrefixWith(cacheName -> commonProperties.getRedisCache().getKeyPrefix() + ":" + cacheName + ":")
                // 设置cache-value序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer));
    }

    @Bean("defaultRedisSerializer")
    @ConditionalOnMissingBean(RedisSerializer.class)
    public RedisSerializer<Object> redisSerializer() {
        RedisSerializer<Object> redisSerializer;
        if (RedisCacheProperties.SerializerEnum.JDK.equals(commonProperties.getRedisCache().getSerializer())) {
            redisSerializer = new JdkSerializationRedisSerializer();
            log.info("[RedisCache] Using JdkSerializationRedisSerializer.class for cache");
        } else if (RedisCacheProperties.SerializerEnum.FAST_JSON.equals(commonProperties.getRedisCache().getSerializer())) {
            redisSerializer = new FastJsonSerializer<>(Object.class);
            log.info("[RedisCache] Using FastJsonSerializer.class for cache");

            // FastJson需指定AutoType序列化白名单
            for (String autoType : commonProperties.getRedisCache().getAutoTypes()) {
                ParserConfig.getGlobalInstance().addAccept(autoType);
            }
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            // 序列化时使用@type字段存储非final类型对象的类信息
            TypeResolverBuilder<?> typer = new ObjectMapper.DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping.NON_FINAL);
            typer = typer.init(JsonTypeInfo.Id.CLASS, null);
            typer = typer.inclusion(JsonTypeInfo.As.PROPERTY);
            typer.typeProperty("@type");
            objectMapper.setDefaultTyping(typer);
            // 设置只序列化非空字段
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            // 设置反序列化时存在未知属性不抛出异常
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            redisSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
            log.info("[RedisCache] Using Jackson2JsonRedisSerializer.class for cache");
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
        if (!StringUtils.hasText(commonProperties.getRedisCache().getKeyPrefix())) {
            log.warn("[RedisCache] Redis key prefix not found, consider setting one");
        }
        return new RedisCache(commonProperties.getRedisCache().getKeyPrefix(), commonProperties.getRedisCache().isPrintOps(), redisTemplate);
    }
}
