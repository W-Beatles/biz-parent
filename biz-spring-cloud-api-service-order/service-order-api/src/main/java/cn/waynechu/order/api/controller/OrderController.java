package cn.waynechu.order.api.controller;

import cn.waynechu.facade.common.response.BizResponse;
import cn.waynechu.order.domain.service.OrderService;
import cn.waynechu.order.facade.response.OrderDetailResponse;
import cn.waynechu.order.facade.response.OrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhuwei
 * @since 2019/9/19 17:48
 */
@RestController
@Api(tags = "订单")
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "根据订单id获取订单信息", notes = "查询order从库")
    @GetMapping("/{orderId}")
    public BizResponse<OrderResponse> getById(@PathVariable Long orderId) {
        OrderResponse orderResponse = orderService.getById(orderId);
        return BizResponse.success(orderResponse);
    }

    @ApiOperation(value = "根据订单id获取订单详情", notes = "查询order从库和product服务")
    @GetMapping("/{orderId}/detail")
    public BizResponse<OrderDetailResponse> getDetailById(@PathVariable Long orderId) {
        OrderDetailResponse orderdetailResponse = orderService.getDetailById(orderId);
        return BizResponse.success(orderdetailResponse);
    }

    @ApiOperation(value = "下单", notes = "测试分布式事务")
    @GetMapping("/placeOrder")
    public BizResponse<Void> placeOrder(@RequestParam Long userId, @RequestParam Long productId, @RequestParam Integer amount) {
        orderService.placeOrder(userId, productId, amount);
        return BizResponse.success();
    }
}
