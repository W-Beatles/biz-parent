package cn.waynechu.archetype.portal.api.controller;

import cn.waynechu.archetype.portal.facade.request.SearchTaskRequest;
import cn.waynechu.archetype.portal.facade.response.SearchTaskResponse;
import cn.waynechu.facade.common.page.BizPageInfo;
import cn.waynechu.facade.common.response.BizResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhuwei
 * @date 2020-06-18 00:17
 */
@RestController
@Api(tags = "任务")
@RequestMapping("/tasks")
public class TaskController {

    @ApiOperation("任务列表查询")
    @PostMapping("/search")
    public BizResponse<BizPageInfo<SearchTaskResponse>> search(@RequestBody SearchTaskRequest request) {
        return BizResponse.success();
    }
}
