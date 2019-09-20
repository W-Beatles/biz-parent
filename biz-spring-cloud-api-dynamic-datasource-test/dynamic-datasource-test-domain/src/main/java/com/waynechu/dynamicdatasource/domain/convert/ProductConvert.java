package com.waynechu.dynamicdatasource.domain.convert;

import cn.waynechu.springcloud.common.util.BeanUtil;
import com.waynechu.dynamicdatasource.dal.dataobject.product.ProductDO;
import com.waynechu.dynamicdatasource.facade.response.ProductResponse;
import lombok.experimental.UtilityClass;

/**
 * @author zhuwei
 * @date 2019/9/20 14:39
 */
@UtilityClass
public class ProductConvert {

    public static ProductResponse toProductResponse(ProductDO product) {
        return BeanUtil.beanTransfer(product, ProductResponse.class);
    }
}
