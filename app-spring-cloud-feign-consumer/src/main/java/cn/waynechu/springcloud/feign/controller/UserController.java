package cn.waynechu.springcloud.feign.controller;

import cn.waynechu.facade.common.response.BizResponse;
import cn.waynechu.springcloud.feign.remote.UserRemote;
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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRemote userRemote;

    @GetMapping("/{id}")
    public BizResponse<Map<String, Object>> getById(@PathVariable Integer id) {
        return userRemote.getById(id);
    }
}
