package cn.waynechu.renting.web.controller;

import cn.waynechu.renting.facade.dto.HouseDTO;
import cn.waynechu.renting.facade.model.ModelHouse;
import cn.waynechu.renting.facade.request.HouseCreateReq;
import cn.waynechu.renting.facade.request.HouseSearchReq;
import cn.waynechu.renting.facade.request.HouseUpdateReq;
import cn.waynechu.renting.web.convert.requset.HouseRequestConvert;
import cn.waynechu.renting.web.service.HouseWebService;
import cn.waynechu.webcommon.annotation.MethodPrintAnnotation;
import cn.waynechu.webcommon.page.PageInfo;
import cn.waynechu.webcommon.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhuwei
 * @date 2018/11/14 16:35
 */
@Api(tags = "房屋信息")
@RestController
@RequestMapping(value = "/houses", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class HouseController {

    @Autowired
    private HouseWebService houseWebService;

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据房屋ID获取房屋详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "房屋ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "city", value = "城市名称", required = true, paramType = "query")
    })
    @MethodPrintAnnotation
    public Result<ModelHouse> getById(@PathVariable Long id) {
        return Result.success(houseWebService.getById(id));
    }

    @PostMapping
    public Result<Boolean> createHouse(@RequestBody HouseCreateReq houseCreateReq) {
        HouseDTO houseDTO = HouseRequestConvert.toHouseDTO(houseCreateReq);
        return Result.success(houseWebService.create(houseDTO));
    }

    @PutMapping
    public Result<Boolean> updateHouse(@Validated @RequestBody HouseUpdateReq houseUpdateReq) {
        HouseDTO houseDTO = HouseRequestConvert.toHouseDTO(houseUpdateReq);
        return Result.success(houseWebService.update(houseDTO));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> removeById(@PathVariable Long id) {
        return Result.success(houseWebService.removeById(id));
    }


    @PostMapping("/search")
    public Result<PageInfo<ModelHouse>> search(@RequestBody HouseSearchReq houseSearchReq,
                                               @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        HouseDTO houseDTO = HouseRequestConvert.toHouseDTO(houseSearchReq);
        return Result.success(houseWebService.search(houseDTO, pageNum, pageSize));
    }

    @PostMapping("/{id}")
    public Result<Boolean> copyByIdTransition(@PathVariable Long id) {
        return Result.success(houseWebService.copyByIdTransition(id));
    }
}
