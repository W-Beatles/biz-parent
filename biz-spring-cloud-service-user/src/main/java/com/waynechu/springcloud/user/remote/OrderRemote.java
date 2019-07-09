package com.waynechu.springcloud.user.remote;

import cn.waynechu.facade.common.response.BizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @author zhuwei
 * @date 2019/4/26 15:13
 */
@FeignClient(name = "ext-order-service-api", path = "/orders", fallback = OrderRemoteFallback.class)
public interface OrderRemote {

    @GetMapping("/{id}")
    BizResponse<Map<String, Object>> getById(@PathVariable Integer id);
}
