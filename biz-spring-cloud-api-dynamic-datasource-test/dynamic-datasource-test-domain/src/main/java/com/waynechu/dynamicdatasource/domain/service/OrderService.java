package com.waynechu.dynamicdatasource.domain.service;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import cn.waynechu.springcloud.common.util.BeanUtil;
import com.waynechu.dynamicdatasource.dal.dataobject.order.OrderDO;
import com.waynechu.dynamicdatasource.dal.dataobject.product.ProductDO;
import com.waynechu.dynamicdatasource.domain.convert.OrderConvert;
import com.waynechu.dynamicdatasource.domain.repository.OrderRepository;
import com.waynechu.dynamicdatasource.domain.repository.ProductRepository;
import com.waynechu.dynamicdatasource.facade.response.OrderDetailResponse;
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

    @Autowired
    private ProductRepository productRepository;

    public OrderResponse getById(Long orderId) {
        OrderDO order = orderRepository.getById(orderId);
        return OrderConvert.toOrderResponse(order);
    }

    public OrderDetailResponse getDetailById(Long orderId) {
        OrderDO order = orderRepository.getById(orderId);
        if (order == null) {
            throw new BizException(BizErrorCodeEnum.SUCCESS_NOT_EXIST);
        }

        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
        BeanUtil.copyProperties(order, orderDetailResponse);

        ProductDO product = productRepository.getById(order.getProductId());
        if (product != null) {
            BeanUtil.copyProperties(product, orderDetailResponse);
        }
        return orderDetailResponse;
    }
}
