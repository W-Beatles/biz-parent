package com.waynechu.renting.web.controller;

import cn.waynechu.facade.common.page.PageInfo;
import cn.waynechu.facade.common.response.BizResponse;
import com.waynechu.renting.web.request.SysDictionaryCreateRequest;
import com.waynechu.renting.web.request.SysDictionarySearchRequest;
import com.waynechu.renting.web.request.SysDictionaryUpdateRequest;
import com.waynechu.renting.web.response.SysDictionaryResponse;
import com.waynechu.renting.web.service.SysDictionaryWebService;
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
 * @date 2019/1/18 14:06
 */
@Api(tags = "系统字典")
@RestController
@RequestMapping(value = "/dictionaries", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SysDictionaryController {

    @Autowired
    private SysDictionaryWebService sysDictionaryWebService;

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据字典ID获取字典详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "字典ID", required = true, paramType = "path")
    })
    public BizResponse<SysDictionaryResponse> getById(@PathVariable Long id) {
        return new BizResponse<>(sysDictionaryWebService.getById(id));
    }

    @PostMapping
    @ApiOperation(value = "添加字典项")
    public BizResponse<Boolean> createSysDictionary(@Validated @RequestBody SysDictionaryCreateRequest request) {
        return new BizResponse<>(sysDictionaryWebService.create(request));
    }

    @PutMapping
    @ApiOperation(value = "更新字典项")
    public BizResponse<Boolean> updateSysDictionary(@Validated @RequestBody SysDictionaryUpdateRequest request) {
        return new BizResponse<>(sysDictionaryWebService.update(request));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除指定字典项")
    public BizResponse<Boolean> removeById(@PathVariable Long id) {
        return new BizResponse<>(sysDictionaryWebService.removeById(id));
    }

    @PostMapping("/search")
    @ApiOperation(value = "分页搜索字典项")
    public BizResponse<PageInfo<SysDictionaryResponse>> search(@RequestBody SysDictionarySearchRequest request) {
        return new BizResponse<>(sysDictionaryWebService.search(request));
    }
}
