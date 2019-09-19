package com.waynechu.dynamicdatasource.domain.repository;

import com.waynechu.dynamicdatasource.dal.dataobject.OrderDO;
import com.waynechu.dynamicdatasource.dal.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author zhuwei
 * @date 2019/9/19 18:38
 */
@Repository
public class OrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    public OrderDO getById(Long orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }
}
