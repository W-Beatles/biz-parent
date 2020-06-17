package cn.waynechu.order.domain.convert;

import cn.waynechu.springcloud.common.util.BeanUtil;
import cn.waynechu.order.dal.dataobject.order.OrderDO;
import cn.waynechu.order.facade.response.OrderResponse;
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
