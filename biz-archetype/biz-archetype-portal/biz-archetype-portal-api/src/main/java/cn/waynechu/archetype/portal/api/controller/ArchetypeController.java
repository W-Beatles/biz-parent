package cn.waynechu.archetype.portal.api.controller;

import cn.waynechu.archetype.portal.domain.service.ArchetypeService;
import cn.waynechu.archetype.portal.facade.request.CreateArchetypeRequest;
import cn.waynechu.archetype.portal.facade.request.SearchArchetypeRequest;
import cn.waynechu.archetype.portal.facade.response.SearchArchetypeResponse;
import cn.waynechu.facade.common.page.BizPageInfo;
import cn.waynechu.facade.common.response.BizResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author zhuwei
 * @date 2020-06-18 00:17
 */
@RestController
@Api(tags = "项目原型")
@RequestMapping("/archetypes")
public class ArchetypeController {

    @Autowired
    private ArchetypeService archetypeService;

    @ApiOperation("查询项目原型列表")
    @PostMapping("/search")
    public BizResponse<BizPageInfo<SearchArchetypeResponse>> search(@RequestBody SearchArchetypeRequest request) {
        BizPageInfo<SearchArchetypeResponse> pageInfo = archetypeService.search(request);
        return BizResponse.success(pageInfo);
    }

    @ApiOperation("新增项目原型")
    @PostMapping
    public BizResponse<Long> create(@Valid @RequestBody CreateArchetypeRequest request) {
        Long id = archetypeService.create(request);
        return BizResponse.success(id);
    }

    @ApiOperation("下载项目原型")
    @GetMapping("/download/{id}")
    public void download(@ApiParam(name = "原型id") @PathVariable Long id, HttpServletResponse response) {
        archetypeService.download(id, response);
    }
}
