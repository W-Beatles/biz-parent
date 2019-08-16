package com.waynechu.utility.controller;

import com.waynechu.utility.domain.properties.ShortUrlProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhuwei
 * @date 2019/8/13 20:47
 */
@Controller
@RequestMapping("/s")
public class ShortUrlController {

    @Autowired
    private ShortUrlProperty shortUrlProperty;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/{shortUrlSuffix}")
    public String redirect(@PathVariable String shortUrlSuffix) {
        return "redirect:" + redisTemplate.opsForValue().get(shortUrlProperty.getRedisUrlKeyPrefix()
                + shortUrlProperty.getShortUrlPrefix() + shortUrlSuffix);
    }
}
