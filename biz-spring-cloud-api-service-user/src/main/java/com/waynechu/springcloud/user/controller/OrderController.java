package com.waynechu.springcloud.user.controller;

import cn.waynechu.facade.common.response.BizResponse;
import com.waynechu.springcloud.user.remote.OrderRemote;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhuwei
 * @date 2019/4/26 15:13
 */
@Slf4j
@RestController
@RequestMapping("/orders/remote")
@Api(tags = "订单信息")
public class OrderController {

    @Autowired
    private OrderRemote orderRemote;

    @ApiOperation("【远程】根据订单id获取订单信息")
    @GetMapping("/{id}")
    BizResponse<Map<String, Object>> getById(@PathVariable Integer id, HttpServletRequest request) {
        log.info(request.getHeader("requestId"));
        return orderRemote.getById(id);
    }
}
