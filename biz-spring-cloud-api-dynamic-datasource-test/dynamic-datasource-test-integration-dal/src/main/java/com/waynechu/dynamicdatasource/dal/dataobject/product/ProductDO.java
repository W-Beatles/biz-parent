package com.waynechu.dynamicdatasource.dal.dataobject.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author zhuwei
 * @date 2019/9/20 15:35
 */
@Data
@TableName(value = "tbl_product")
public class ProductDO {
    /**
     * 套餐ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 套餐名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 套餐状态：0下架，1上架
     */
    @TableField(value = "product_status")
    private Boolean productStatus;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_PRODUCT_STATUS = "product_status";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}