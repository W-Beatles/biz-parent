package cn.waynechu.renting.facade.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author zhuwei
 * @date 2018/11/15 11:54
 */
@Data
@ApiModel(description = "添加房屋信息请求对象")
public class HouseCreateRequest {

    private String title;

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
