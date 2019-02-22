package cn.waynechu.renting.web.controller;

import cn.waynechu.renting.facade.dto.SysDictionaryDTO;
import cn.waynechu.renting.facade.model.ModelSysDictionary;
import cn.waynechu.renting.facade.request.SysDictionaryCreateReq;
import cn.waynechu.renting.facade.request.SysDictionarySearchReq;
import cn.waynechu.renting.facade.request.SysDictionaryUpdateReq;
import cn.waynechu.renting.web.convert.requset.SysDictionaryRequestConvert;
import cn.waynechu.renting.web.service.SysDictionaryWebService;
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
    @MethodPrintAnnotation
    public Result<ModelSysDictionary> getById(@PathVariable Long id) {
        return Result.success(sysDictionaryWebService.getById(id));
    }

    @PostMapping
    @MethodPrintAnnotation
    public Result<Boolean> createSysDictionary(@RequestBody SysDictionaryCreateReq sysDictionaryCreateReq) {
        SysDictionaryDTO sysDictionaryDTO = SysDictionaryRequestConvert.toSysDictionaryDTO(sysDictionaryCreateReq);
        return Result.success(sysDictionaryWebService.create(sysDictionaryDTO));
    }

    @PutMapping
    @MethodPrintAnnotation
    public Result<Boolean> updateSysDictionary(@RequestBody SysDictionaryUpdateReq sysDictionaryUpdateReq) {
        SysDictionaryDTO sysDictionaryDTO = SysDictionaryRequestConvert.toSysDictionaryDTO(sysDictionaryUpdateReq);
        return Result.success(sysDictionaryWebService.update(sysDictionaryDTO));
    }

    @DeleteMapping("/{id}")
    @MethodPrintAnnotation
    public Result<Boolean> removeById(@PathVariable Long id) {
        return Result.success(sysDictionaryWebService.removeById(id));
    }


    @PostMapping("/search")
    @MethodPrintAnnotation
    public Result<PageInfo<ModelSysDictionary>> search(@RequestBody SysDictionarySearchReq sysDictionarySearchReq,
                                                       @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        SysDictionaryDTO sysDictionaryDTO = SysDictionaryRequestConvert.toSysDictionaryDTO(sysDictionarySearchReq);
        return Result.success(sysDictionaryWebService.search(sysDictionaryDTO, pageNum, pageSize));
    }
}
