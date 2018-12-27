package cn.waynechu.renting.facade.model;

import cn.waynechu.webcommon.web.model.AbstractModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author zhuwei
 * @date 2018/11/15 13:29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "房屋信息返回对象")
public class ModelHouse extends AbstractModel {
    private Long id;

    private String title;

    private Integer price;

    private Integer area;

    private Integer room;

    private Integer floor;

    private Integer totalFloor;

    private Integer watchTimes;

    private Integer buildYear;

    private Integer status;

    private Date createTime;

    private Date lastUpdateTime;

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
