package cn.waynechu.renting.web.controller;

import cn.waynechu.common.annotation.MethodPrintAnnotation;
import cn.waynechu.renting.core.service.HouseService;
import cn.waynechu.renting.dal.entity.House;
import cn.waynechu.renting.facade.model.common.Result;
import cn.waynechu.renting.facade.request.HouseCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhuwei
 * @date 2018/11/14 16:35
 */
@RestController
@RequestMapping("/houses")
public class HouseController {
    @Autowired
    private HouseService houseService;

    @GetMapping("/{id}")
    @MethodPrintAnnotation(isFormat = true)
    public House getById(@PathVariable Long id, @RequestParam String city) {
        House house = houseService.getById(id);
        return house;
    }

    @PostMapping
    @MethodPrintAnnotation(isFormat = true)
    public Result<Boolean> createHouse(@RequestBody HouseCreateRequest houseCreateRequest) {
        Result<Boolean> returnValue = new Result<>();
        returnValue.setData(true);
        return returnValue;
    }
}
