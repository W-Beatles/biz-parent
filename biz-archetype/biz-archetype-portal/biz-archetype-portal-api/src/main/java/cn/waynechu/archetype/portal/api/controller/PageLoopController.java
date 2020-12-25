package cn.waynechu.archetype.portal.api.controller;

import cn.waynechu.archetype.portal.domain.service.ArchetypeService;
import cn.waynechu.archetype.portal.facade.request.SearchArchetypeRequest;
import cn.waynechu.archetype.portal.facade.response.SearchArchetypeResponse;
import cn.waynechu.facade.common.response.BizResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhuwei
 * @date 2020-12-25 22:51
 */
@RestController
@Api(tags = "分页查询工具测试")
@RequestMapping("/page-loop")
public class PageLoopController {

    @Autowired
    private ArchetypeService archetypeService;

    @ApiOperation("测试单线程循环翻页")
    @PostMapping("/all-page")
    public BizResponse<List<SearchArchetypeResponse>> listAllPage(@RequestBody SearchArchetypeRequest request) {
        List<SearchArchetypeResponse> archetypeResponseList = archetypeService.listAllPage(request);
        return BizResponse.success(archetypeResponseList);
    }

    @ApiOperation("测试多线程并发翻页")
    @PostMapping("/all-page-concurrency")
    public BizResponse<List<SearchArchetypeResponse>> listAllPageConcurrency(@RequestBody SearchArchetypeRequest request) {
        List<SearchArchetypeResponse> archetypeResponseList = archetypeService.listAllPageConcurrency(request);
        return BizResponse.success(archetypeResponseList);
    }

    @ApiOperation("测试多线程并发翻页")
    @PostMapping("/all-page-concurrency2")
    public BizResponse<List<SearchArchetypeResponse>> listAllPageConcurrency2(@RequestBody SearchArchetypeRequest request) {
        List<SearchArchetypeResponse> archetypeResponseList = archetypeService.listAllPageConcurrency2(request);
        return BizResponse.success(archetypeResponseList);
    }
}
