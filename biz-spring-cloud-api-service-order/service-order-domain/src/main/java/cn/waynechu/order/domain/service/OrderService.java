package cn.waynechu.order.domain.service;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import cn.waynechu.order.common.enums.OrderStatusEnum;
import cn.waynechu.order.dal.dataobject.order.OrderDO;
import cn.waynechu.order.domain.convert.OrderConvert;
import cn.waynechu.order.domain.repository.OrderRepository;
import cn.waynechu.order.facade.response.OrderDetailResponse;
import cn.waynechu.order.facade.response.OrderResponse;
import cn.waynechu.order.remote.model.response.ProductResponse;
import cn.waynechu.springcloud.common.util.BeanUtil;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author zhuwei
 * @since 2019/9/19 18:13
 */
@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    /**
     * 根据订单id获取订单信息
     *
     * @param orderId 订单id
     * @return 订单信息
     */
    public OrderResponse getById(Long orderId) {
        OrderDO order = orderRepository.getById(orderId);
        return OrderConvert.toOrderResponse(order);
    }

    /**
     * 根据订单id获取订单详情
     *
     * @param orderId 订单id
     * @return 订单详情
     */
    @GlobalTransactional
    public OrderDetailResponse getDetailById(Long orderId) {
        // 状态判断、时效性要求高的查询优先走主库，防止主从同步延迟导致读取脏数据
        OrderDO order = orderRepository.getByIdFromMaster(orderId);
        if (order == null) {
            throw new BizException(BizErrorCodeEnum.DATA_NOT_EXIST);
        }

        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
        BeanUtil.copyProperties(order, orderDetailResponse);

        ProductResponse product = productService.getById(order.getProductId());
        if (product != null) {
            BeanUtil.copyProperties(product, orderDetailResponse);
        }
        return orderDetailResponse;
    }

    public void placeOrder(Long userId, Long productId) {
        log.info("当前 XID: {}", RootContext.getXID());

        OrderDO orderDO = new OrderDO();
        orderDO.setUserId(userId);
        orderDO.setProductId(productId);
        orderDO.setOrderStatus(OrderStatusEnum.INIT.getCode());
        orderDO.setCreateTime(LocalDateTime.now());
        Integer createOrderFlag = orderRepository.create(orderDO);

        log.info("保存订单{}", createOrderFlag > 0 ? "成功" : "失败");

        // 扣减库存
        productService.reduceStock(productId, 1);
    }
}
