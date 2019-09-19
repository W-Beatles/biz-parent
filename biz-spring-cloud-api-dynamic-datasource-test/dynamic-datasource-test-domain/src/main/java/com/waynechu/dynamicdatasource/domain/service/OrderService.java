package com.waynechu.dynamicdatasource.domain.service;

import com.waynechu.dynamicdatasource.dal.dataobject.OrderDO;
import com.waynechu.dynamicdatasource.domain.convert.OrderConvert;
import com.waynechu.dynamicdatasource.domain.repository.OrderRepository;
import com.waynechu.dynamicdatasource.facade.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhuwei
 * @date 2019/9/19 18:13
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderResponse getById(Long orderId) {
        OrderDO order = orderRepository.getById(orderId);
        return OrderConvert.toOrderResponse(order);
    }
}
