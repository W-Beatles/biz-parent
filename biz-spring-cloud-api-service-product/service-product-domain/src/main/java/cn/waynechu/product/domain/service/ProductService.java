package cn.waynechu.product.domain.service;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import cn.waynechu.product.dal.dataobject.product.ProductDO;
import cn.waynechu.product.domain.convert.ProductConvert;
import cn.waynechu.product.domain.repository.ProductRepository;
import cn.waynechu.product.facade.model.response.ProductResponse;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhuwei
 * @since 2019/9/20 14:36
 */
@Slf4j
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

    /**
     * 扣减库存
     *
     * @param productId 产品id
     * @param amount    数量
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void reduceStock(Long productId, Integer amount) {
        log.info("当前 XID: {}", RootContext.getXID());
        ProductDO productDO = productRepository.getById(productId);

        Integer residue = productDO.getResidue();
        if (residue < amount) {
            log.warn("库存不足, 商品编号: {}, 当前库存: {}", productId, residue);
            throw new BizException(BizErrorCodeEnum.OPERATION_FAILED, "库存不足");
        }
        productRepository.reduceStock(productId, amount);
    }
}
