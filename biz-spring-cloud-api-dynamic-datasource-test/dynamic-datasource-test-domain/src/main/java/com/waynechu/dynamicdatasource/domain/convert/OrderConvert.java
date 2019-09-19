package com.waynechu.dynamicdatasource.domain.convert;

import cn.waynechu.springcloud.common.util.BeanUtil;
import com.waynechu.dynamicdatasource.dal.dataobject.OrderDO;
import com.waynechu.dynamicdatasource.facade.response.OrderResponse;
import lombok.experimental.UtilityClass;

/**
 * @author zhuwei
 * @date 2019/9/19 18:40
 */
@UtilityClass
public class OrderConvert {

    public static OrderResponse toOrderResponse(OrderDO order) {
        return BeanUtil.beanTransfer(order, OrderResponse.class);
    }
}
