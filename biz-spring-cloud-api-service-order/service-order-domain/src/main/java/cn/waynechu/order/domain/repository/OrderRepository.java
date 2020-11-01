package cn.waynechu.order.domain.repository;

import cn.waynechu.bootstarter.dynamicdatasource.annotion.SwitchDataSource;
import cn.waynechu.order.common.enums.OrderStatusEnum;
import cn.waynechu.order.dal.dataobject.order.OrderDO;
import cn.waynechu.order.dal.mapper.order.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author zhuwei
 * @since 2019/9/19 18:38
 */
@Repository
public class OrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 根据订单id获取订单信息
     *
     * @param orderId 订单id
     * @return 订单信息
     */
    public OrderDO getById(Long orderId) {
        return orderMapper.selectById(orderId);
    }

    /**
     * 根据订单id获取订单信息(查主库)
     *
     * @param orderId 订单id
     * @return 订单信息
     */
    @SwitchDataSource("order-master")
    public OrderDO getByIdFromMaster(Long orderId) {
        return orderMapper.selectById(orderId);
    }

    /**
     * 创建订单
     *
     * @param orderDO 订单信息
     * @return 影响行数
     */
    public Integer create(OrderDO orderDO) {
        return orderMapper.insert(orderDO);
    }

    /**
     * 更新订单状态
     *
     * @param orderId 订单id
     * @param status  订单状态
     */
    public void updateStatus(Long orderId, OrderStatusEnum status) {
        OrderDO orderDO = new OrderDO();
        orderDO.setId(orderId);
        orderDO.setOrderStatus(status.getCode());
        orderMapper.updateById(orderDO);
    }
}
