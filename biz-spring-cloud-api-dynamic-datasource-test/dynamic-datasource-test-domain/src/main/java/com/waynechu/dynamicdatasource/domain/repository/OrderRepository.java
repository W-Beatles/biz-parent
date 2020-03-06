package com.waynechu.dynamicdatasource.domain.repository;

import cn.waynechu.bootstarter.dynamicdatasource.annotion.SwitchDataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.waynechu.dynamicdatasource.dal.dataobject.order.OrderDO;
import com.waynechu.dynamicdatasource.dal.mapper.order.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhuwei
 * @date 2019/9/19 18:38
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

    public List<OrderDO> listAll() {
        QueryWrapper<OrderDO> wrapper = new QueryWrapper<>();
        return orderMapper.selectList(wrapper);
    }
}
