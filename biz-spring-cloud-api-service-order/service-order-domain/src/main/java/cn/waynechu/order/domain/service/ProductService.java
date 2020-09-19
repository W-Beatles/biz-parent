package cn.waynechu.order.domain.service;

import cn.waynechu.facade.common.response.BizResponse;
import cn.waynechu.order.remote.model.response.ProductResponse;
import cn.waynechu.order.remote.remote.ProductRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhuwei
 * @since 2019/9/20 14:36
 */
@Service
public class ProductService {

    @Autowired
    private ProductRemote productRemote;

    /**
     * 根据产品id获取产品信息
     *
     * @param productId 产品id
     * @return 产品信息
     */
    public ProductResponse getById(Long productId) {
        BizResponse<ProductResponse> response = productRemote.getById(productId);
        if (response != null && response.isSuccess()) {
            return response.getData();
        }
        return null;
    }
}
