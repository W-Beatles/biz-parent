package com.waynechu.utility.controller;

import cn.waynechu.facade.common.response.BizResponse;
import cn.waynechu.springcloud.common.annotation.DistributedLock;
import com.waynechu.utility.domain.service.RegionService;
import com.waynechu.utility.facade.response.RegionResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuwei
 * @date 2019/8/8 15:08
 */
@RestController
@Api(tags = "行政区")
@RequestMapping("/regions")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @ApiOperation(value = "省市区三级联动", notes = "根据父级id获取子级行政区id列表")
    @ApiImplicitParam(name = "pid", value = "父级行政区id。pid为100000查询省列表", required = true)
    @GetMapping
    public BizResponse<List<RegionResponse>> listByPid(Integer pid) {
        return BizResponse.success(regionService.listByPid(pid));
    }
}
