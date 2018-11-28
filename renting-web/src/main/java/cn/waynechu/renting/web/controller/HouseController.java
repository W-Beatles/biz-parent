package cn.waynechu.renting.web.controller;

import cn.waynechu.common.annotation.MethodPrintAnnotation;
import cn.waynechu.renting.core.service.HouseService;
import cn.waynechu.renting.dal.entity.House;
import com.github.pagehelper.Page;
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
    public House getById(@PathVariable Long id) {
        return houseService.getById(id);
    }

    @PostMapping
    @MethodPrintAnnotation
    public boolean createHouse(@RequestBody House house) {
        return houseService.create(house);
    }

    @PutMapping
    @MethodPrintAnnotation
    public boolean updateHouse(@RequestBody House house) {
        return houseService.update(house);
    }

    @DeleteMapping("/{id}")
    @MethodPrintAnnotation
    public boolean deleteById(@PathVariable Long id) {
        return houseService.deleteById(id);
    }

    @PostMapping("/search")
    public PageInfo<House> search(@RequestBody House house, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return houseService.search(house, pageNum, pageSize);
    }

    @PostMapping("/{id}")
    @MethodPrintAnnotation
    public boolean copyByIdTransition(@PathVariable Long id) {
        return houseService.copyByIdTransition(id);
    }
}
