package cn.waynechu.renting.web.controller;

import cn.waynechu.renting.facade.dto.ShopConfigLogDTO;
import cn.waynechu.renting.facade.request.ExportLogReq;
import cn.waynechu.renting.web.service.ShopConfigWebService;
import cn.waynechu.webcommon.annotation.MethodPrintAnnotation;
import cn.waynechu.webcommon.util.ExcelUtil;
import cn.waynechu.webcommon.web.ModelExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ShopConfigWebService shopConfigService;

    @PostMapping(value = "/exportLog")
    @ApiOperation(value = "导出变更日志")
    @MethodPrintAnnotation(description = "导出变更日志")
    public void exportLog(@Validated @RequestBody ExportLogReq request, HttpServletResponse response) {
        ModelExcel<ShopConfigLogDTO> modelExcel = shopConfigService.exportLog(request.getStartDate(), request.getEndDate());
        ExcelUtil.exportExcel(response, modelExcel);
    }
}