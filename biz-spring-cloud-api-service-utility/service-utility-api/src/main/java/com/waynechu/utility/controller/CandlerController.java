package com.waynechu.utility.controller;

import cn.waynechu.facade.common.response.BizResponse;
import com.waynechu.utility.domain.service.CandlerService;
import com.waynechu.utility.facade.response.CandlerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuwei
 * @date 2020-04-18 18:35
 */
@RestController
@Api(tags = "日历服务")
@RequestMapping("/calendars")
public class CandlerController {

    @Autowired
    private CandlerService candlerService;

    @ApiOperation(value = "获取指定年月的日历信息")
    @GetMapping
    public BizResponse<CandlerResponse> getCandler(@ApiParam(value = "年份", required = true, example = "2020")
                                                   @RequestParam Integer year,
                                                   @ApiParam(value = "月份", required = true, example = "8")
                                                   @RequestParam Integer month) {
        CandlerResponse candler = candlerService.getCandler(year, month);
        return BizResponse.success(candler);
    }

}
