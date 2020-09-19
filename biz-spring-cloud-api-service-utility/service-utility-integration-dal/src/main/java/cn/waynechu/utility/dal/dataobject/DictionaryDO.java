package cn.waynechu.utility.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhuwei
 * @since 2020-07-04 15:28
 */
@Data
@TableName(value = "dictionary")
public class DictionaryDO implements Serializable {
    /**
     * 字典id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父节点id
     */
    @TableField(value = "pid")
    private Long pid;

    /**
     * 类型编码
     */
    @TableField(value = "dic_type_code")
    private String dicTypeCode;

    /**
     * 字典编码
     */
    @TableField(value = "dic_code")
    private String dicCode;

    /**
     * 字典值
     */
    @TableField(value = "dic_value")
    private String dicValue;

    /**
     * 字典描述
     */
    @TableField(value = "dic_desc")
    private String dicDesc;

    /**
     * 排序号
     */
    @TableField(value = "sort_num")
    private Integer sortNum;

    /**
     * 是否固定: 0否 1是
     */
    @TableField(value = "fixed_status")
    private Integer fixedStatus;

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

    public static final String COL_PID = "pid";

    public static final String COL_DIC_TYPE_CODE = "dic_type_code";

    public static final String COL_DIC_CODE = "dic_code";

    public static final String COL_DIC_VALUE = "dic_value";

    public static final String COL_DIC_DESC = "dic_desc";

    public static final String COL_SORT_NUM = "sort_num";

    public static final String COL_FIXED_STATUS = "fixed_status";

    public static final String COL_CREATED_USER = "created_user";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_USER = "updated_user";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DELETED_STATUS = "deleted_status";
}