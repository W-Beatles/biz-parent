package com.waynechu.renting.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zhuwei
 * @date 2018/12/29 10:32
 */
@Data
@ApiModel(description = "更新房屋信息请求对象")
public class HouseUpdateRequest {

    @ApiModelProperty("房屋ID")
    @NotNull(message = "房屋ID不能为空")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("价格")
    private Integer price;

    @ApiModelProperty("面积")
    private Integer area;

    @ApiModelProperty("卧室数量")
    private Integer room;

    @ApiModelProperty("楼层")
    private Integer floor;

    @ApiModelProperty("总楼层")
    private Integer totalFloor;

    @ApiModelProperty("被看次数")
    private Integer watchTimes;

    @ApiModelProperty("建立年限")
    private Integer buildYear;

    @ApiModelProperty("房屋状态：0未审核 1审核通过 2已出租 3逻辑删除")
    private Integer status;

    @ApiModelProperty("城市标记简写 如: 北京bj")
    private String cityEnName;

    @ApiModelProperty("地区英文简写 如: 昌平区 cpq")
    private String regionEnName;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("房屋朝向")
    private Integer direction;

    @ApiModelProperty("距地铁距离 默认-1 附近无地铁")
    private Integer distanceToSubway;

    @ApiModelProperty("客厅数量")
    private Integer parlour;

    @ApiModelProperty("所在小区")
    private String district;

    @ApiModelProperty("所属管理员ID")
    private Long adminId;

    @ApiModelProperty("卫生间数量")
    private Integer bathroom;

    @ApiModelProperty("街道")
    private String street;

    @ApiModelProperty("更新人")
    @NotNull(message = "更新人不能为空")
    private String updateUser;

    @ApiModelProperty("更新时间")
    @NotNull(message = "更新时间不能为空")
    private Date updateTime;
}
