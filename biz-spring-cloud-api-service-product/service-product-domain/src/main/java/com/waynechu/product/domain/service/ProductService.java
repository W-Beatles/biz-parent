package com.waynechu.product.domain.service;

import com.waynechu.product.dal.dataobject.product.ProductDO;
import com.waynechu.product.domain.convert.ProductConvert;
import com.waynechu.product.domain.repository.ProductRepository;
import com.waynechu.product.facade.model.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhuwei
 * @date 2019/9/20 14:36
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * 根据产品id获取产品信息
     *
     * @param productId 产品id
     * @return 产品信息
     */
    public ProductResponse getById(Long productId) {
        ProductDO product = productRepository.getById(productId);
        return ProductConvert.toProductResponse(product);
    }
}
