package cn.waynechu.product.domain.convert;

import cn.waynechu.springcloud.common.util.BeanUtil;
import cn.waynechu.product.dal.dataobject.product.ProductDO;
import cn.waynechu.product.facade.model.response.ProductResponse;
import lombok.experimental.UtilityClass;

/**
 * @author zhuwei
 * @since 2019/9/20 14:39
 */
@UtilityClass
public class ProductConvert {

    public static ProductResponse toProductResponse(ProductDO product) {
        return BeanUtil.beanTransfer(product, ProductResponse.class);
    }
}
