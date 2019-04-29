package cn.waynechu.springcloud.order.controller;

import cn.waynechu.facade.common.response.BizResponse;
import cn.waynechu.springcloud.order.remote.UserRemote;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zhuwei
 * @date 2019/4/25 16:45
 */
@RestController
@RequestMapping("/users/remote")
@Api(tags = "用户信息")
public class UserController {

    @Autowired
    private UserRemote userRemote;

    @ApiOperation(value = "获取用户详情")
    @GetMapping("/{id}")
    public BizResponse<Map<String, Object>> getById(@PathVariable Integer id) {
        return userRemote.getById(id);
    }
}
