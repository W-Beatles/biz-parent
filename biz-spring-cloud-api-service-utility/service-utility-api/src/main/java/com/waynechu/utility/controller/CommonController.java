package com.waynechu.utility.controller;

import cn.waynechu.facade.common.response.BizResponse;
import com.waynechu.utility.domain.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuwei
 * @date 2019/8/13 10:17
 */
@RestController
@Api(tags = "通用服务")
@RequestMapping("/commons")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @ApiOperation("获取短链")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "originUrl", value = "原始url", required = true),
            @ApiImplicitParam(name = "timeout", value = "过期时间/分钟。不传该参数则短链永久有效")
    })
    @GetMapping("/get-short-url")
    public BizResponse<String> getShortUrl(String originUrl, @RequestParam(required = false) Long timeout) {
        return BizResponse.success(commonService.getShortUrl(originUrl, timeout));
    }

    @ApiOperation("短链还原")
    @ApiImplicitParam(name = "shortUrl", value = "短url", required = true)
    @GetMapping("/get-origin-url")
    public BizResponse<String> getOriginUrl(String shortUrl) {
        return BizResponse.success(commonService.getOriginUrl(shortUrl));
    }
}
