package cn.waynechu.product.dal.dataobject.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhuwei
 * @date 2020-11-01 17:21
 */
@Data
@TableName(value = "tbl_product")
public class ProductDO {
    /**
     * 产品ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 产品名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 产品状态：0下架，1上架
     */
    @TableField(value = "product_status")
    private Boolean productStatus;

    /**
     * 总量
     */
    @TableField(value = "total")
    private Integer total;

    /**
     * 使用量
     */
    @TableField(value = "used")
    private Integer used;

    /**
     * 剩余量
     */
    @TableField(value = "residue")
    private Integer residue;

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

    public static final String COL_TOTAL = "total";

    public static final String COL_USED = "used";

    public static final String COL_RESIDUE = "residue";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}