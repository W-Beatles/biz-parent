package cn.waynechu.utility.domain.service;

import cn.waynechu.springcloud.common.util.BinaryUtil;
import cn.waynechu.utility.common.consts.RedisPrefix;
import cn.waynechu.utility.domain.properties.ShortUrlProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuwei
 * @date 2019/8/13 10:20
 */
@Service
public class ShortUrlService {

    @Autowired
    private ShortUrlProperty shortUrlProperty;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取短链
     *
     * @param originUrl 原始地址
     * @param timeout   过期时间
     * @return 短链
     */
    public String getShortUrl(String originUrl, Long timeout) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Long increment = null;
        while (increment == null) {
            increment = valueOperations.increment(shortUrlProperty.getRedisUrlKeyPrefix() + RedisPrefix.ShortUrls.INCREMENT_KEY);
        }

        String shortUrlSuffix = BinaryUtil.convert10to62(increment);
        String shortUrl = shortUrlProperty.getShortUrlPrefix() + shortUrlSuffix;
        // 短网址 -> 原始地址的映射  key: redisUrlKeyPrefix + shortUrl  value: originUrl
        if (timeout == null) {
            valueOperations.set(shortUrlProperty.getRedisUrlKeyPrefix() + shortUrl, originUrl);
        } else {
            valueOperations.set(shortUrlProperty.getRedisUrlKeyPrefix() + shortUrl, originUrl, timeout, TimeUnit.MINUTES);
        }
        return shortUrl;
    }

    /**
     * 短链还原
     *
     * @param shortUrl 短链
     * @return 原始地址
     */
    public String getOriginUrl(String shortUrl) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(shortUrlProperty.getRedisUrlKeyPrefix() + shortUrl);
    }

    /**
     * 短链访问统计
     *
     * @param shortUrl 短链
     */
    public void asyncStatistic(String shortUrl) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.increment(shortUrlProperty.getRedisUrlKeyPrefix() + RedisPrefix.ShortUrls.STATISTICS_PREFIX + shortUrl);
    }

    /**
     * 获取短链访问次数
     *
     * @param shortUrl 短链
     * @return 访问次数
     */
    public Long statistics(String shortUrl) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        return Long.valueOf(Optional.ofNullable(valueOperations.get(shortUrlProperty.getRedisUrlKeyPrefix() + RedisPrefix.ShortUrls.STATISTICS_PREFIX + shortUrl)).orElse("0"));
    }
}
