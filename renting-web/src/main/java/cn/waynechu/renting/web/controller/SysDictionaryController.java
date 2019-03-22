package cn.waynechu.renting.web.controller;

import cn.waynechu.renting.facade.dto.SysDictionaryDTO;
import cn.waynechu.renting.facade.model.ModelSysDictionary;
import cn.waynechu.renting.facade.request.SysDictionaryCreateRequest;
import cn.waynechu.renting.facade.request.SysDictionarySearchRequest;
import cn.waynechu.renting.facade.request.SysDictionaryUpdateRequest;
import cn.waynechu.renting.web.convert.requset.SysDictionaryRequestConvert;
import cn.waynechu.renting.web.service.SysDictionaryWebService;
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
 * @date 2019/1/18 14:06
 */
@Api(tags = "系统字典")
@RestController
@RequestMapping(value = "/dictionary", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SysDictionaryController {

    @Autowired
    private SysDictionaryWebService sysDictionaryWebService;

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据字典ID获取字典详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "字典ID", required = true, paramType = "path")
    })
    public Result<ModelSysDictionary> getById(@PathVariable Long id) {
        return Result.success(sysDictionaryWebService.getById(id));
    }

    @PostMapping
    public Result<Boolean> createSysDictionary(@RequestBody SysDictionaryCreateRequest sysDictionaryCreateReq) {
        SysDictionaryDTO sysDictionaryDTO = SysDictionaryRequestConvert.toSysDictionaryDTO(sysDictionaryCreateReq);
        return Result.success(sysDictionaryWebService.create(sysDictionaryDTO));
    }

    @PutMapping
    public Result<Boolean> updateSysDictionary(@RequestBody SysDictionaryUpdateRequest sysDictionaryUpdateRequest) {
        SysDictionaryDTO sysDictionaryDTO = SysDictionaryRequestConvert.toSysDictionaryDTO(sysDictionaryUpdateRequest);
        return Result.success(sysDictionaryWebService.update(sysDictionaryDTO));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> removeById(@PathVariable Long id) {
        return Result.success(sysDictionaryWebService.removeById(id));
    }


    @PostMapping("/search")
    public Result<PageInfo<ModelSysDictionary>> search(@RequestBody SysDictionarySearchRequest sysDictionarySearchRequest,
                                                       @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        SysDictionaryDTO sysDictionaryDTO = SysDictionaryRequestConvert.toSysDictionaryDTO(sysDictionarySearchRequest);
        return Result.success(sysDictionaryWebService.search(sysDictionaryDTO, pageNum, pageSize));
    }
}
