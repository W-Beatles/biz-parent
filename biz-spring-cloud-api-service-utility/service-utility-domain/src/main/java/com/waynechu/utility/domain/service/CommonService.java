package com.waynechu.utility.domain.service;

import com.waynechu.utility.common.util.BinaryConversionUtil;
import com.waynechu.utility.domain.properties.ShortUrlProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author zhuwei
 * @date 2019/8/13 10:20
 */
@Service
public class CommonService {

    @Autowired
    private ShortUrlProperty shortUrlProperty;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String getShortUrl(String originUrl, Long timeout) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Long increment = null;
        while (increment == null) {
            increment = valueOperations.increment(shortUrlProperty.getRedisIncrementKey());
        }
        String shortUrlSuffix = BinaryConversionUtil.convert10to62(increment);
        String shortUrl = shortUrlProperty.getShortUrlPrefix() + shortUrlSuffix;
        // 短网址 -> 原始地址的映射  key: redisUrlKeyPrefix + shortUrl  value: originUrl
        if (timeout == null) {
            valueOperations.set(shortUrlProperty.getRedisUrlKeyPrefix() + shortUrl, originUrl);
        } else {
            valueOperations.set(shortUrlProperty.getRedisUrlKeyPrefix() + shortUrl, originUrl, timeout, TimeUnit.MINUTES);
        }
        return shortUrl;
    }

    public String getOriginUrl(String shortUrl) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(shortUrlProperty.getRedisUrlKeyPrefix() + shortUrl);
    }
}
