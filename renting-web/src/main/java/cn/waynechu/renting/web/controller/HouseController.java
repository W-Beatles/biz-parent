package cn.waynechu.renting.web.controller;

import cn.waynechu.common.annotation.MethodPrintAnnotation;
import cn.waynechu.common.facade.Result;
import cn.waynechu.renting.core.service.HouseService;
import cn.waynechu.renting.dal.entity.House;
import cn.waynechu.renting.facade.model.ModelHouse;
import com.github.pagehelper.PageInfo;
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
    @MethodPrintAnnotation
    public Result<ModelHouse> getById(@PathVariable Long id, @RequestParam String city) {
        return Result.success(houseService.getById(id));
    }

    @PostMapping
    @MethodPrintAnnotation
    public Result<Boolean> createHouse(@RequestBody House house) {
        return Result.success(houseService.create(house));
    }

    @PutMapping
    @MethodPrintAnnotation
    public Result<Boolean> updateHouse(@RequestBody House house) {
        return Result.success(houseService.update(house));
    }

    @DeleteMapping("/{id}")
    @MethodPrintAnnotation
    public Result<Boolean> deleteById(@PathVariable Long id) {
        return Result.success(houseService.deleteById(id));
    }

    @PostMapping("/search")
    public Result<PageInfo<House>> search(@RequestBody House house, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return Result.success(houseService.search(house, pageNum, pageSize));
    }

    @PostMapping("/{id}")
    @MethodPrintAnnotation
    public Result<Boolean> copyByIdTransition(@PathVariable Long id) {
        return Result.success(houseService.copyByIdTransition(id));
    }
}
