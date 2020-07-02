package cn.waynechu.utility.api.controller;

import cn.waynechu.facade.common.response.BizResponse;
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
 * @date 2020-03-22 23:06
 */
@RestController
@Api(tags = "Excel导出服务")
@RequestMapping("/excels")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @ApiOperation("获取sid")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "url", value = "导出URL", example = "/order-service/orders/export", required = true),
            @ApiImplicitParam(name = "params", value = "导出参数")
    })
    @PostMapping("/sid")
    public BizResponse<String> getSid(@RequestParam String url, @RequestBody JSONObject params) {
        String sid = excelService.getSid(url, params);
        return BizResponse.success(sid);
    }
}
