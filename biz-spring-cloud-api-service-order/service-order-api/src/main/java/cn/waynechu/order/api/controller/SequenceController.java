package cn.waynechu.order.api.controller;

import cn.waynechu.bootstarter.sequence.generator.IdGenerator;
import cn.waynechu.facade.common.response.BizResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhuwei
 * @since 2020/9/17 18:02
 */
@RestController
@Api(tags = "分布式ID测试")
@RequestMapping("/ids")
public class SequenceController {

    @Autowired
    private IdGenerator idGenerator;

    @ApiOperation("获取id")
    @GetMapping
    public BizResponse<Long> generateId(@RequestParam String bizTag) {
        long id = idGenerator.nextId(bizTag);
        return BizResponse.success(id);
    }

    @ApiOperation("批量获取id")
    @GetMapping("/batch")
    public BizResponse<List<String>> batchGenerateId(@RequestParam String bizTag, @RequestParam Integer quantity) {
        String[] ids = idGenerator.nextStringIds(bizTag, quantity);
        return BizResponse.success(Arrays.asList(ids));
    }
}
