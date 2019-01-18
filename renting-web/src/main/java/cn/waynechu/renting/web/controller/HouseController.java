package cn.waynechu.renting.web.controller;

import cn.waynechu.renting.facade.dto.HouseDTO;
import cn.waynechu.renting.facade.model.ModelHouse;
import cn.waynechu.renting.facade.request.HouseCreateRequest;
import cn.waynechu.renting.facade.request.HouseSearchRequest;
import cn.waynechu.renting.facade.request.HouseUpdateRequest;
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
    public Result<ModelHouse> getById(@PathVariable Long id, @RequestParam String city) {
        return Result.success(houseWebService.getById(id));
    }

    @PostMapping
    @MethodPrintAnnotation
    public Result<Boolean> createHouse(@RequestBody HouseCreateRequest houseCreateRequest) {
        HouseDTO houseDTO = HouseRequestConvert.toHouseDTO(houseCreateRequest);
        return Result.success(houseWebService.create(houseDTO));
    }

    @PutMapping
    @MethodPrintAnnotation
    public Result<Boolean> updateHouse(@RequestBody HouseUpdateRequest houseUpdateRequest) {
        HouseDTO houseDTO = HouseRequestConvert.toHouseDTO(houseUpdateRequest);
        return Result.success(houseWebService.update(houseDTO));
    }

    @DeleteMapping("/{id}")
    @MethodPrintAnnotation
    public Result<Boolean> removeById(@PathVariable Long id) {
        return Result.success(houseWebService.removeById(id));
    }


    @PostMapping("/search")
    @MethodPrintAnnotation
    public Result<PageInfo<ModelHouse>> search(@RequestBody HouseSearchRequest houseSearchRequest,
                                               @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        HouseDTO houseDTO = HouseRequestConvert.toHouseDTO(houseSearchRequest);
        return Result.success(houseWebService.search(houseDTO, pageNum, pageSize));
    }

    @PostMapping("/{id}")
    @MethodPrintAnnotation
    public Result<Boolean> copyByIdTransition(@PathVariable Long id) {
        return Result.success(houseWebService.copyByIdTransition(id));
    }
}
