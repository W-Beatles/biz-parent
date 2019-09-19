package com.waynechu.dynamicdatasource.dal.dataobject;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author zhuwei
 * @date 2019/9/19 19:50
 */
@Data
public class OrderDO {
    /**
    * 订单ID
    */
    private Long id;

    /**
    * 用户ID
    */
    private Long userId;

    /**
    * 套餐ID
    */
    private Long productId;

    /**
    * 订单状态：1已下单，2已送达（部门代理人领餐成功），3已领取（代理人确认成员领餐），4未领取（9点后代理人未确认领取的全部设为未领取）。取消订单删除该条记录）
    */
    private Integer orderStatus;

    /**
    * 创建时间
    */
    private LocalDateTime createTime;

    /**
    * 更新时间
    */
    private LocalDateTime updateTime;
}