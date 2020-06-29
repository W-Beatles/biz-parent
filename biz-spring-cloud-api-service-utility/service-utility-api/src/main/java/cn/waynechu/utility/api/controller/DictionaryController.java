package cn.waynechu.utility.api.controller;

import cn.waynechu.facade.common.response.BizResponse;
import cn.waynechu.utility.domain.service.DictionaryService;
import cn.waynechu.utility.facade.request.CreateDicTypeRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuwei
 * @date 2020-06-28 23:15
 */
@RestController
@Api(tags = "字典服务")
@RequestMapping("/dictionaries")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @ApiOperation("添加字典类型")
    @PostMapping("/")
    public BizResponse<Long> create(CreateDicTypeRequest request) {
        Long id = dictionaryService.create(request);
        return BizResponse.success(id);
    }
}
