package com.waynechu.springcloud.order.remote;

import cn.waynechu.facade.common.response.BizResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @author zhuwei
 * @date 2019/4/25 15:37
 */
@FeignClient(name = "user-service-api", path = "/users", fallback = UserRemoteFallback.class)
public interface UserRemote {

    @GetMapping("/{id}")
    BizResponse<Map<String, Object>> getById(@PathVariable Integer id);
}
