package cn.waynechu.utility.api.controller;

import cn.waynechu.facade.common.response.BizResponse;
import com.alibaba.fastjson.JSONObject;
import cn.waynechu.utility.domain.service.ExcelService;
import io.swagger.annotations.Api;
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
    @PostMapping("/sid")
    public BizResponse<String> getSid(@RequestBody JSONObject params, @RequestParam String dataExportUrl) {
        String sid = excelService.getSid(params, dataExportUrl);
        return BizResponse.success(sid);
    }
}
