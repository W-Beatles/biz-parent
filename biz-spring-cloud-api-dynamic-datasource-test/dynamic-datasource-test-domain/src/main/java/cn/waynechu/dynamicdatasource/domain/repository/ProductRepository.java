package cn.waynechu.dynamicdatasource.domain.repository;

import cn.waynechu.dynamicdatasource.dal.dataobject.product.ProductDO;
import cn.waynechu.dynamicdatasource.dal.mapper.product.ProductMapper;
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

    /**
     * 根据产品id获取产品信息
     *
     * @param productId 产品id
     * @return 产品信息
     */
    public ProductDO getById(Long productId) {
        return productMapper.selectById(productId);
    }
}
