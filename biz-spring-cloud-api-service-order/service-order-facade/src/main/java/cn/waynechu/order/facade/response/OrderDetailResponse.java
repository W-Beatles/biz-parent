package cn.waynechu.order.facade.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhuwei
 * @since 2019/9/20 14:46
 */
@Data
@ApiModel(description = "订单详情返回对象")
public class OrderDetailResponse {

    @ApiModelProperty("订单ID")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("产品ID")
    private Long productId;

    @ApiModelProperty("产品名称")
    private String name;

    @ApiModelProperty("产品状态：0下架，1上架")
    private Boolean productStatus;

    @ApiModelProperty("订单状态: 1创建 2成功 3失败")
    private Integer orderStatus;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
