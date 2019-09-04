package com.waynechu.springcloud.user.controller;

import cn.waynechu.facade.common.response.BizResponse;
import com.waynechu.springcloud.user.request.TestRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author zhuwei
 * @date 2019/9/4 15:06
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试接口")
public class TestController {

    @ApiOperation("测试请求参数格式不正确异常")
    @PostMapping
    public BizResponse<Boolean> testHttpMessageNotReadableException(@Valid @RequestBody TestRequest request) {
        return BizResponse.success(true);
    }
}
