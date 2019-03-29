package com.waynechu.renting.facade.dto;

import com.waynechu.renting.facade.service.HouseService;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhuwei
 * @date 2018/12/28 20:08
 */
@Data
public class HouseDTO implements Serializable {

    /**
     * 房屋ID
     * 更新时不允许为空，添加时允许为空
     */
    @NotNull(groups = HouseService.Update.class)
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

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private Boolean isDeleted;
}
