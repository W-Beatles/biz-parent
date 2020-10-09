package cn.waynechu.archetype.portal.api.controller;

import cn.waynechu.archetype.portal.domain.service.ArchetypeService;
import cn.waynechu.archetype.portal.facade.request.CreateArchetypeRequest;
import cn.waynechu.archetype.portal.facade.request.SearchArchetypeRequest;
import cn.waynechu.archetype.portal.facade.request.UpdateArchetypeRequest;
import cn.waynechu.archetype.portal.facade.response.ArchetypeResponse;
import cn.waynechu.archetype.portal.facade.response.SearchArchetypeResponse;
import cn.waynechu.facade.common.page.BizPageInfo;
import cn.waynechu.facade.common.response.BizResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Objects;

/**
 * @author zhuwei
 * @since 2020-06-18 00:17
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

    @HystrixCommand(
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value = "100")
            }
    )
    @ApiOperation("导出项目原型列表")
    @PostMapping("/export")
    public BizResponse<String> export(@RequestBody SearchArchetypeRequest request) {
        String sid = archetypeService.export(request);
        return BizResponse.success(sid);
    }

    @ApiOperation("根据原型id获取原型信息")
    @GetMapping("/{id}")
    public BizResponse<ArchetypeResponse> getById(@ApiParam(value = "原型id", required = true, example = "0") @PathVariable Long id) {
        ArchetypeResponse response = archetypeService.getById(id);
        return BizResponse.success(response);
    }

    @ApiOperation("新增项目原型")
    @PostMapping
    public BizResponse<Long> create(@Valid @RequestBody CreateArchetypeRequest request) {
        Long id = archetypeService.create(request);
        return BizResponse.success(id);
    }

    @ApiOperation(value = "编辑项目原型", notes = "编辑后会重新生成原型文件")
    @PutMapping("/{id}")
    public BizResponse<Long> update(@ApiParam(value = "原型id", required = true, example = "0") @PathVariable Long id, @Valid @RequestBody UpdateArchetypeRequest request) {
        Assert.isTrue(Objects.equals(id, request.getId()), "参数错误");
        Long id2 = archetypeService.update(request);
        return BizResponse.success(id2);
    }

    @ApiOperation("删除项目原型")
    @DeleteMapping("/{id}")
    public BizResponse<Void> remove(@ApiParam(value = "原型id", required = true, example = "0") @PathVariable Long id) {
        archetypeService.remove(id);
        return BizResponse.success();
    }

    @ApiOperation("下载项目原型")
    @GetMapping("/download/{id}")
    public void download(@ApiParam(value = "原型id", required = true, example = "0") @PathVariable Long id, HttpServletResponse response) {
        archetypeService.download(id, response);
    }
}
