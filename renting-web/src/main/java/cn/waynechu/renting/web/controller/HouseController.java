package cn.waynechu.renting.web.controller;

import cn.waynechu.renting.core.service.HouseService;
import cn.waynechu.renting.dal.entity.House;
import cn.waynechu.common.annotation.ControllerLogAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhuwei
 * @date 2018/11/14 16:35
 */
@RestController
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @GetMapping("/{id}")
    @ControllerLogAnnotation(description = "获取房屋信息")
    public House getById(@PathVariable Long id) {
        return houseService.getById(id);
    }
}
