package com.waynechu.utility.domain.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhuwei
 * @date 2019/8/13 11:20
 */
@Data
@Component
@ConfigurationProperties(prefix = "utility.short-url")
public class ShortUrlProperty {

    /**
     * 短网址前缀
     */
    private String shortUrlPrefix;

    /**
     * 存储短网址 到 原始地址的映射  key: redisUrlKeyPrefix + shortUrl  value: originUrl
     */
    private String redisUrlKeyPrefix;

}
