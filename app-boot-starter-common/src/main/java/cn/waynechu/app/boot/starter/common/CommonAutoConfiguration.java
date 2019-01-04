package cn.waynechu.app.boot.starter.common;

import cn.waynechu.app.boot.starter.common.properties.CommonProperties;
import cn.waynechu.app.boot.starter.common.filter.MDCFilter;
import cn.waynechu.app.boot.starter.common.util.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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

    @Bean(name = "commonRedisCache")
    @ConditionalOnProperty(value = "common.redis-cache.enable", havingValue = "true")
    public RedisCache redisCache(StringRedisTemplate redisTemplate) {
        if (!StringUtils.hasText(commonProperties.getRedisCache().getKeyPrefix())) {
            log.warn("[RedisCache] ****** Redis key prefix not found, consider setting one. ******");
        }
        return new RedisCache(commonProperties.getRedisCache().getKeyPrefix(), commonProperties.getRedisCache().isPrintOps(), redisTemplate);
    }

    @Bean
    @ConditionalOnProperty(value = "common.mdc.enable", havingValue = "true")
    @SuppressWarnings("unchecked")
    public FilterRegistrationBean mdcFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        MDCFilter mdcFilter = new MDCFilter();
        mdcFilter.setMdcPrefix(commonProperties.getMdc().getPrefix());

        registrationBean.setFilter(mdcFilter);
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
