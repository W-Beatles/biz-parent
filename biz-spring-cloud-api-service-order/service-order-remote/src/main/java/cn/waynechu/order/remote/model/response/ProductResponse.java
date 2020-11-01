package cn.waynechu.order.remote.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhuwei
 * @since 2019/9/20 14:36
 */
@Data
@ApiModel(description = "产品信息返回对象")
public class ProductResponse {

    @ApiModelProperty("产品ID")
    private Long id;

    @ApiModelProperty("产品名称")
    private String name;

    @ApiModelProperty("产品状态：0下架，1上架")
    private Boolean productStatus;

    @ApiModelProperty("总量")
    private Integer total;

    @ApiModelProperty("使用量")
    private Integer used;

    @ApiModelProperty("剩余量")
    private Integer residue;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
