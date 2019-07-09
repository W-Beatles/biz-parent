package com.waynechu.springcloud.order.controller;

import cn.waynechu.facade.common.response.BizResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author zhuwei
 * @date 2019/4/26 15:10
 */
@Slf4j
@RestController
@RequestMapping("/orders")
@Api(tags = "订单信息")
public class OrderController {

    @ApiOperation(value = "获取订单详情")
    @GetMapping("/{id}")
    public BizResponse<Map<String, Object>> getById(@PathVariable Integer id) {
        Map<String, Object> order = new LinkedHashMap<>(2);
        order.put("id", id);
        order.put("count", new Random().nextInt());
        log.info(order.toString());
        return BizResponse.success(order);
    }
}
