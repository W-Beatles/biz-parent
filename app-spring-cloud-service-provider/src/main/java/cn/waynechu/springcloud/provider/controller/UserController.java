package cn.waynechu.springcloud.provider.controller;

import cn.waynechu.facade.common.response.BizResponse;
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
 * @date 2019/4/25 15:06
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{id}")
    public BizResponse<Map<String, Object>> getById(@PathVariable Integer id) {
        Map<String, Object> user = new LinkedHashMap<>(2);
        user.put("id", id);
        Random random = new Random();
        user.put("name", (char) (0x4e00 + random.nextInt() * (0x9fa5 - 0x4e00 + 1)) + "" + (char) (0x4e00 + random.nextInt() * (0x9fa5 - 0x4e00 + 1)));
        log.info(user.toString());
        return BizResponse.success(user);
    }
}
