package com.waynechu.dynamicdatasource.dal.dataobject;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author zhuwei
 * @date 2019/9/19 19:50
 */
@Data
public class ProductDO {
    /**
    * 套餐ID
    */
    private Long id;

    /**
    * 套餐名称
    */
    private String name;

    /**
    * 套餐状态：0下架，1上架
    */
    private Boolean productStatus;

    /**
    * 创建时间
    */
    private LocalDateTime createTime;

    /**
    * 更新时间
    */
    private LocalDateTime updateTime;
}