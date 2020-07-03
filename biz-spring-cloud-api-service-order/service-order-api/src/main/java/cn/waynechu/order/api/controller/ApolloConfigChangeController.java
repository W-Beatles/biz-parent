package cn.waynechu.order.api.controller;

import cn.waynechu.facade.common.response.BizResponse;
import cn.waynechu.order.domain.service.ApolloConfigChangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuwei
 * @date 2020/5/15 15:11
 */
@RestController
@Api(tags = "动态配置")
@RequestMapping("/apollo")
public class ApolloConfigChangeController {

    @Autowired
    private ApolloConfigChangeService apolloConfigChangeService;

    @ApiOperation(value = "@Value自动刷新测试")
    @GetMapping("/refreshValue")
    public BizResponse<String> refreshValue() {
        String value = apolloConfigChangeService.refreshValue();
        return BizResponse.success(value);
    }

    @ApiOperation(value = "@ConfigurationProperties自动刷新测试")
    @GetMapping("/refreshConfigurationProperties")
    public BizResponse<String> refreshConfigurationProperties() {
        String value = apolloConfigChangeService.refreshConfigurationProperties();
        return BizResponse.success(value);
    }
}
