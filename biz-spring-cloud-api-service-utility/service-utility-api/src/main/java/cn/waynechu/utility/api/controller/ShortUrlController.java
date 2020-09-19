package cn.waynechu.utility.api.controller;

import cn.waynechu.facade.common.response.BizResponse;
import cn.waynechu.utility.domain.properties.ShortUrlProperty;
import cn.waynechu.utility.domain.service.ShortUrlService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author zhuwei
 * @since 2019/8/13 20:47
 */
@Controller
@Api(tags = "短链服务")
@RequestMapping("/s")
public class ShortUrlController {

    @Autowired
    private ShortUrlProperty shortUrlProperty;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ShortUrlService shortUrlService;

    @ApiOperation("获取短链")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "originUrl", value = "原始url", required = true),
            @ApiImplicitParam(name = "timeout", value = "过期时间/分钟。不传该参数则短链永久有效")
    })
    @GetMapping("/get-short-url")
    @ResponseBody
    public BizResponse<String> getShortUrl(@RequestParam String originUrl, @RequestParam(required = false) Long timeout) {
        return BizResponse.success(shortUrlService.getShortUrl(originUrl, timeout));
    }

    @ApiOperation("短链访问次数统计")
    @GetMapping("/statistics")
    @ResponseBody
    public BizResponse<Long> statistics(@ApiParam(value = "短链url", required = true) @RequestParam String shortUrl) {
        return BizResponse.success(shortUrlService.statistics(shortUrl));
    }

    @ApiOperation("短链还原")
    @GetMapping("/get-origin-url")
    @ResponseBody
    public BizResponse<String> getOriginUrl(@ApiParam(value = "短链url", required = true) @RequestParam String shortUrl) {
        return BizResponse.success(shortUrlService.getOriginUrl(shortUrl));
    }

    @ApiIgnore
    @ApiOperation("短链重定向")
    @GetMapping("/{shortUrlSuffix}")
    public String redirect(@PathVariable String shortUrlSuffix) {
        String shortUrl = shortUrlProperty.getShortUrlPrefix() + shortUrlSuffix;
        shortUrlService.asyncStatistic(shortUrl);
        return "redirect:" + redisTemplate.opsForValue().get(shortUrlProperty.getRedisUrlKeyPrefix() + shortUrl);
    }
}
