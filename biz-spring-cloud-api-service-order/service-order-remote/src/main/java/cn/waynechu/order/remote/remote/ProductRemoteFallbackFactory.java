package cn.waynechu.order.remote.remote;

import cn.waynechu.facade.common.response.BizResponse;
import cn.waynechu.order.remote.model.response.ProductResponse;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhuwei
 * @date 2020-01-09 23:05
 */
@Slf4j
@Component
public class ProductRemoteFallbackFactory implements FallbackFactory<ProductRemote> {

    @Override
    public ProductRemote create(Throwable throwable) {
        return new ProductRemote() {
            @Override
            public BizResponse<ProductResponse> getById(Long productId) {
                log.error("根据产品id获取产品信息调用失败, message: {}", throwable.getMessage());
                return null;
            }
        };
    }
}
