package com.waynechu.springcloud.user.controller;

import cn.waynechu.facade.common.response.BizResponse;
import com.waynechu.springcloud.user.remote.OrderRemote;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zhuwei
 * @date 2019/4/26 15:13
 */
@RestController
@RequestMapping("/orders/remote")
@Api(tags = "订单信息")
public class OrderController {

    @Autowired
    private OrderRemote orderRemote;

    @GetMapping("/{id}")
    BizResponse<Map<String, Object>> getById(@PathVariable Integer id) {
        return orderRemote.getById(id);
    }
}
