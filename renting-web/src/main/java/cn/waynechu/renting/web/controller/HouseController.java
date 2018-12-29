package cn.waynechu.renting.web.controller;

import cn.waynechu.common.annotation.MethodPrintAnnotation;
import cn.waynechu.renting.facade.dto.HouseDTO;
import cn.waynechu.renting.facade.request.HouseCreateReq;
import cn.waynechu.renting.facade.request.HouseSearchReq;
import cn.waynechu.renting.facade.request.HouseUpdateReq;
import cn.waynechu.renting.facade.vo.HouseVO;
import cn.waynechu.renting.web.convert.HouseConvert;
import cn.waynechu.renting.web.convert.dto.HouseDtoConvert;
import cn.waynechu.renting.web.service.HouseWebService;
import cn.waynechu.webcommon.AbstractController;
import cn.waynechu.webcommon.page.PageInfo;
import cn.waynechu.webcommon.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhuwei
 * @date 2018/11/14 16:35
 */
@RestController
@RequestMapping("/houses")
public class HouseController extends AbstractController {

    @Autowired
    private HouseWebService houseWebService;

    @GetMapping("/{id}")
    @MethodPrintAnnotation
    public Result<HouseVO> getById(@PathVariable Long id, @RequestParam String city) {
        return Result.success(houseWebService.getById(id));
    }

    @PostMapping
    @MethodPrintAnnotation
    public Result<Boolean> createHouse(@RequestBody HouseCreateReq houseCreateReq) {
        HouseDTO houseDTO = HouseConvert.convertHouseDTO(houseCreateReq);
        return Result.success(houseWebService.create(houseDTO));
    }

    @PutMapping
    @MethodPrintAnnotation
    public Result<Boolean> updateHouse(@RequestBody HouseUpdateReq houseUpdateReq) {
        HouseDTO houseDTO = HouseDtoConvert.convertHouseDTO(houseUpdateReq);
        return Result.success(houseWebService.update(houseDTO));
    }

    @DeleteMapping("/{id}")
    @MethodPrintAnnotation
    public Result<Boolean> removeById(@PathVariable Long id) {
        return Result.success(houseWebService.removeById(id));
    }


    @PostMapping("/search")
    @MethodPrintAnnotation
    public Result<PageInfo<HouseVO>> search(@RequestBody HouseSearchReq houseSearchReq,
                                            @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        HouseDTO houseDTO = HouseDtoConvert.convertHouseDTO(houseSearchReq);
        return Result.success(houseWebService.search(houseDTO, pageNum, pageSize));
    }

    @PostMapping("/{id}")
    @MethodPrintAnnotation
    public Result<Boolean> copyByIdTransition(@PathVariable Long id) {
        return Result.success(houseWebService.copyByIdTransition(id));
    }
}
