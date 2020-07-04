package cn.waynechu.utility.api.controller;

import cn.waynechu.facade.common.page.BizPageInfo;
import cn.waynechu.facade.common.response.BizResponse;
import cn.waynechu.utility.domain.service.DictionaryTypeService;
import cn.waynechu.utility.facade.request.CreateDicTypeRequest;
import cn.waynechu.utility.facade.request.SearchDicTypeRequest;
import cn.waynechu.utility.facade.response.DicTypeResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author zhuwei
 * @since 2020/7/3 17:32
 */
@RestController
@Api(tags = "字典类型服务")
@RequestMapping("")
public class DictionaryTypeController {

    @Autowired
    private DictionaryTypeService dictionaryTypeService;

    @ApiOperation("搜索字典类型列表")
    @PostMapping("/dictionary-types/search")
    public BizResponse<BizPageInfo<DicTypeResponse>> search(@Valid @RequestBody SearchDicTypeRequest request) {
        BizPageInfo<DicTypeResponse> pageInfo = dictionaryTypeService.search(request);
        return BizResponse.success(pageInfo);
    }

    @ApiOperation("添加字典类型")
    @PostMapping("/dictionary-types")
    public BizResponse<Long> create(@Valid @RequestBody CreateDicTypeRequest request) {
        Long id = dictionaryTypeService.create(request);
        return BizResponse.success(id);
    }

    @ApiOperation("删除字典类型")
    @DeleteMapping("/dictionary-types/{id}")
    public BizResponse<Void> remove(@PathVariable Long id) {
        dictionaryTypeService.remove(id);
        return BizResponse.success();
    }
}
