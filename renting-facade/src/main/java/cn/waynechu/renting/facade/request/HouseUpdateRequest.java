package cn.waynechu.renting.facade.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author zhuwei
 * @date 2018/12/29 10:32
 */
@Data
@ApiModel(description = "更新房屋信息请求对象")
public class HouseUpdateRequest {

    @NotNull(message = "房屋ID不能为空")
    private Long id;

    private String title;

    @Max(value = 1000000, message = "价格不能高于100万")
    @Min(value = 10000, message = "价格不能低于1万")
    private Integer price;

    private Integer area;

    private Integer room;

    private Integer floor;

    private Integer totalFloor;

    private Integer watchTimes;

    private Integer buildYear;

    private Integer status;

    private String cityEnName;

    private String regionEnName;

    private String cover;

    private Integer direction;

    private Integer distanceToSubway;

    private Integer parlour;

    private String district;

    private Long adminId;

    private Integer bathroom;

    private String street;
}
