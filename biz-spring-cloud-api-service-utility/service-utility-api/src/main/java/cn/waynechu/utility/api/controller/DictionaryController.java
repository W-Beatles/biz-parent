package cn.waynechu.utility.api.controller;

import cn.waynechu.facade.common.page.BizPageInfo;
import cn.waynechu.facade.common.response.BizResponse;
import cn.waynechu.utility.domain.service.DictionaryService;
import cn.waynechu.utility.facade.request.CreateDictionaryRequest;
import cn.waynechu.utility.facade.request.SearchDictionaryRequest;
import cn.waynechu.utility.facade.request.UpdateDictionaryRequest;
import cn.waynechu.utility.facade.response.DictionaryMiniResponse;
import cn.waynechu.utility.facade.response.DictionaryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author zhuwei
 * @since 2020-06-28 23:15
 */
@RestController
@Api(tags = "字典服务")
@RequestMapping("/dictionaries")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @ApiOperation("搜索字典列表")
    @PostMapping("/search")
    public BizResponse<BizPageInfo<DictionaryResponse>> search(@Valid @RequestBody SearchDictionaryRequest request) {
        BizPageInfo<DictionaryResponse> pageInfo = dictionaryService.search(request);
        return BizResponse.success(pageInfo);
    }

    @ApiOperation("根据字典类型编码查询字典列表")
    @GetMapping("/list-by-type")
    public BizResponse<List<DictionaryMiniResponse>> listByType(@RequestParam String dicTypeCode) {
        List<DictionaryMiniResponse> responses = dictionaryService.listByType(dicTypeCode);
        return BizResponse.success(responses);
    }

    @ApiOperation("添加字典")
    @PostMapping
    public BizResponse<Long> create(@Valid @RequestBody CreateDictionaryRequest request) {
        Long id = dictionaryService.create(request);
        return BizResponse.success(id);
    }

    @ApiOperation("编辑字典")
    @PutMapping
    public BizResponse<Long> update(@Valid @RequestBody UpdateDictionaryRequest request) {
        Long id = dictionaryService.update(request);
        return BizResponse.success(id);
    }

    @ApiOperation("删除字典")
    @DeleteMapping("/{id}")
    public BizResponse<Void> remove(@PathVariable Long id) {
        dictionaryService.remove(id);
        return BizResponse.success();
    }
}
