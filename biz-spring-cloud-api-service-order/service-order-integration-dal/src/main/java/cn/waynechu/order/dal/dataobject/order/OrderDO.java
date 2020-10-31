package cn.waynechu.order.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhuwei
 * @since 2019/9/20 15:34
 */
@Data
@TableName(value = "tbl_order")
public class OrderDO {
    /**
     * 订单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 套餐ID
     */
    @TableField(value = "product_id")
    private Long productId;

    /**
     * 订单状态: 1创建 2成功 3失败
     */
    @TableField(value = "order_status")
    private Integer orderStatus;

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

    public static final String COL_USER_ID = "user_id";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_ORDER_STATUS = "order_status";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}