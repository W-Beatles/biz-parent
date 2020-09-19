package cn.waynechu.utility.api.controller;

import cn.waynechu.facade.common.response.BizResponse;
import cn.waynechu.springcloud.common.model.ExportResultResponse;
import cn.waynechu.utility.domain.service.ExcelService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhuwei
 * @since 2020-03-22 23:06
 */
@RestController
@Api(tags = "Excel导出服务")
@RequestMapping("/excels")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @ApiOperation(value = "获取sid", notes = "sid为当前excel导出请求唯一标识，用于异步获取导出状态")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "url", value = "导出URL", example = "biz-archetype-portal/archetypes/export", required = true)
    })
    @PostMapping("/export/sid")
    public BizResponse<String> sid(@RequestParam String url, @RequestBody JSONObject params) {
        String sid = excelService.getSid(url, params);
        return BizResponse.success(sid);
    }

    @ApiOperation(value = "查询导出状态", notes = "轮询该接口来获取最新的导出状态，轮询时间推荐从1s开始指数型递增")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "sid", value = "sid", example = "eed6cc4f-a07a-4458-9199-7aaaff64b081", required = true)
    })
    @PostMapping("/export/status")
    public BizResponse<ExportResultResponse> status(@RequestParam String sid) {
        ExportResultResponse response = excelService.status(sid);
        return BizResponse.success(response);
    }
}
