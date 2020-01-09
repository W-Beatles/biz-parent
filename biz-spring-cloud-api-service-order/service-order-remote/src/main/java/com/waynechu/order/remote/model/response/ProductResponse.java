package com.waynechu.order.remote.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhuwei
 * @date 2019/9/20 14:36
 */
@Data
@ApiModel(description = "套餐信息返回对象")
public class ProductResponse {

    @ApiModelProperty("套餐ID")
    private Long id;

    @ApiModelProperty("套餐名称")
    private String name;

    @ApiModelProperty("套餐状态：0下架，1上架")
    private Boolean productStatus;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
