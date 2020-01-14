package com.waynechu.product.api.controller;

import cn.waynechu.facade.common.response.BizResponse;
import com.waynechu.product.domain.service.ProductService;
import com.waynechu.product.facade.model.response.ProductResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuwei
 * @date 2019/9/19 17:50
 */
@RestController
@Api(tags = "产品")
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "根据产品id获取产品信息", notes = "查询product从库")
    @GetMapping("/{productId}")
    public BizResponse<ProductResponse> getById(@PathVariable Long productId) {
        ProductResponse productResponse = productService.getById(productId);
        return BizResponse.success(productResponse);
    }
}
