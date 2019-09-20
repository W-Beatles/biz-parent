package com.waynechu.dynamicdatasource.api.controller;

import cn.waynechu.facade.common.response.BizResponse;
import com.waynechu.dynamicdatasource.domain.service.OrderService;
import com.waynechu.dynamicdatasource.facade.response.OrderDetailResponse;
import com.waynechu.dynamicdatasource.facade.response.OrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuwei
 * @date 2019/9/19 17:48
 */
@RestController
@Api(tags = "订单")
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("根据订单id获取订单信息")
    @GetMapping("/{orderId}")
    public BizResponse<OrderResponse> getById(@PathVariable Long orderId) {
        OrderResponse orderResponse = orderService.getById(orderId);
        return BizResponse.success(orderResponse);
    }

    @ApiOperation("根据订单id获取订单详情")
    @GetMapping("/{orderId}/detail")
    public BizResponse<OrderDetailResponse> getDetailById(@PathVariable Long orderId) {
        OrderDetailResponse orderdetailResponse = orderService.getDetailById(orderId);
        return BizResponse.success(orderdetailResponse);
    }
}
