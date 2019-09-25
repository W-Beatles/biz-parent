package com.waynechu.springcloud.order.controller;

import cn.waynechu.facade.common.response.BizResponse;
import com.waynechu.springcloud.order.remote.UserRemote;
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
 * @date 2019/4/25 16:45
 */
@Slf4j
@RestController
@RequestMapping("/users/remote")
@Api(tags = "用户信息")
public class UserController {

    @Autowired
    private UserRemote userRemote;

    @ApiOperation(value = "根据用户id获取用户信息")
    @GetMapping("/{id}")
    public BizResponse<Map<String, Object>> getById(@PathVariable Integer id, HttpServletRequest request) {
        log.info(request.getHeader("requestId"));
        return userRemote.getById(id);
    }
}
