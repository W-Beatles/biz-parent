package com.waynechu.dynamicdatasource.domain.repository;

import com.waynechu.dynamicdatasource.dal.dataobject.product.ProductDO;
import com.waynechu.dynamicdatasource.dal.mapper.product.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author zhuwei
 * @date 2019/9/20 14:39
 */
@Repository
public class ProductRepository {

    @Autowired
    private ProductMapper productMapper;

    public ProductDO getById(Long productId) {
        return productMapper.selectById(productId);
    }
}
