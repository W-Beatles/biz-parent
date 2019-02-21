package cn.waynechu.renting.web.controller;

import cn.waynechu.renting.facade.request.ExportLogRequest;
import cn.waynechu.webcommon.annotation.MethodPrintAnnotation;
import cn.waynechu.webcommon.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhuwei
 * @date 2019/2/21 16:37
 */
@Api(tags = "门店配置")
@RestController
@RequestMapping(value = "/shopConfig", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ShopConfigController {

    @PostMapping(value = "/exportLog")
    @ApiOperation(value = "导出变更日志")
    @MethodPrintAnnotation(description = "导出变更日志")
    public Result exportLog(@Validated @RequestBody ExportLogRequest request, HttpServletResponse response) {
        return Result.success();
    }
}