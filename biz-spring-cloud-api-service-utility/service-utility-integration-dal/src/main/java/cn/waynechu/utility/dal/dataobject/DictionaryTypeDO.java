package cn.waynechu.utility.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author zhuwei
 * @date 2020-06-28 23:22
 */
@Data
@TableName(value = "dictionary_type")
public class DictionaryTypeDO implements Serializable {
    /**
     * 类型id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 类型编码
     */
    @TableField(value = "type_code")
    private String typeCode;

    /**
     * 所属AppID
     */
    @TableField(value = "app_id")
    private String appId;

    /**
     * 类型描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 创建人
     */
    @TableField(value = "created_user")
    private String createdUser;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private LocalDateTime createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_user")
    private String updatedUser;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    private LocalDateTime updatedTime;

    /**
     * 是否删除: 0否 1是
     */
    @TableField(value = "deleted_status")
    private Boolean deletedStatus;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_TYPE_CODE = "type_code";

    public static final String COL_APP_ID = "app_id";

    public static final String COL_DESCRIPTION = "description";

    public static final String COL_CREATED_USER = "created_user";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_USER = "updated_user";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DELETED_STATUS = "deleted_status";
}