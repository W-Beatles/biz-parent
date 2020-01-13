package com.waynechu.order.remote.remote;

import cn.waynechu.facade.common.response.BizResponse;
import com.waynechu.order.remote.model.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zhuwei
 * @date 2020-01-09 22:54
 */
@FeignClient(name = "service-product-api", path = "/products", fallbackFactory = ProductRemoteFallbackFactory.class)
public interface ProductRemote {

    /**
     * 根据产品id获取产品信息
     */
    @GetMapping("/{productId}")
    BizResponse<ProductResponse> getById(@PathVariable Long productId);
}
