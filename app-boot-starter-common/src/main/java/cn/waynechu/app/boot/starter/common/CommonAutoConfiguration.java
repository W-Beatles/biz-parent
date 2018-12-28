package cn.waynechu.app.boot.starter.common;

import cn.waynechu.app.boot.starter.common.properties.CommonProperties;
import cn.waynechu.app.boot.starter.common.util.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

/**
 * @author zhuwei
 * @date 2018/12/28 16:16
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({CommonProperties.class})
public class CommonAutoConfiguration {

    @Autowired
    private CommonProperties commonProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Bean(name = "commonRedisCache")
    @ConditionalOnProperty("common.redis-key-prefix")
    public RedisCache redisCache() {
        if (!StringUtils.hasText(commonProperties.getRedisKeyPrefix())) {
            log.warn("[RedisCache] Redis key prefix not found, consider setting one.");
        }
        return new RedisCache(commonProperties.getRedisKeyPrefix(), redisTemplate);
    }
}
