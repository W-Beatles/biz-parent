package cn.waynechu.renting.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zhuwei
 * @date 2018/11/15 11:54
 */
@Data
@ApiModel(description = "添加房屋信息请求对象")
public class HouseCreateRequest {

    @ApiModelProperty("标题")
    @NotNull(message = "标题不能为空")
    private String title;

    @ApiModelProperty("价格")
    @NotNull(message = "价格不能为空")
    private Integer price;

    @ApiModelProperty("面积")
    @NotNull(message = "面积不能为空")
    private Integer area;

    @ApiModelProperty("卧室数量")
    @NotNull(message = "卧室数量不能为空")
    private Integer room;

    @ApiModelProperty("楼层")
    @NotNull(message = "楼层不能为空")
    private Integer floor;

    @ApiModelProperty("总楼层")
    @NotNull(message = "总楼层不能为空")
    private Integer totalFloor;

    @ApiModelProperty("被看次数")
    private Integer watchTimes;

    @ApiModelProperty("建立年限")
    @NotNull(message = "建立年限不能为空")
    private Integer buildYear;

    @ApiModelProperty("房屋状态：0未审核 1审核通过 2已出租 3逻辑删除")
    @NotNull(message = "房屋状态不能为空")
    private Integer status;

    @ApiModelProperty("城市标记简写 如: 北京bj")
    @NotNull(message = "城市标记简写不能为空")
    private String cityEnName;

    @ApiModelProperty("地区英文简写 如: 昌平区 cpq")
    @NotNull(message = "地区英文简写不能为空")
    private String regionEnName;

    @ApiModelProperty("封面")
    @NotNull(message = "封面不能为空")
    private String cover;

    @ApiModelProperty("房屋朝向")
    @NotNull(message = "房屋朝向不能为空")
    private Integer direction;

    @ApiModelProperty("距地铁距离 默认-1 附近无地铁")
    @NotNull(message = "距地铁距离不能为空")
    private Integer distanceToSubway;

    @ApiModelProperty("客厅数量")
    @NotNull(message = "客厅数量不能为空")
    private Integer parlour;

    @ApiModelProperty("所在小区")
    @NotNull(message = "所在小区不能为空")
    private String district;

    @ApiModelProperty("所属管理员ID")
    @NotNull(message = "所属管理员ID不能为空")
    private Long adminId;

    @ApiModelProperty("卫生间数量")
    @NotNull(message = "卫生间数量不能为空")
    private Integer bathroom;

    @ApiModelProperty("街道")
    @NotNull(message = "街道不能为空")
    private String street;

    @ApiModelProperty("创建人")
    @NotNull(message = "创建人不能为空")
    private String createUser;

    @ApiModelProperty("创建时间")
    @NotNull(message = "创建时间不能为空")
    private Date createTime;
}
