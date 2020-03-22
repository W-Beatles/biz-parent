package com.waynechu.utility.controller;

import cn.waynechu.facade.common.response.BizResponse;
import com.alibaba.fastjson.JSONObject;
import com.waynechu.utility.domain.service.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhuwei
 * @date 2020-03-22 23:06
 */
@Controller
@Api(tags = "excel下载")
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @ApiOperation("excel导出-获取sid")
    @PostMapping("/sid")
    public BizResponse<String> getSid(@RequestBody JSONObject params, @RequestParam String dataExportUrl) {
        String sid = excelService.getSid(params, dataExportUrl);
        return BizResponse.success(sid);
    }
}
