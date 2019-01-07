package cn.waynechu.renting.facade.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuwei
 * @date 2018/12/29 10:32
 */
@Data
public class HouseUpdateReq implements Serializable {
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
