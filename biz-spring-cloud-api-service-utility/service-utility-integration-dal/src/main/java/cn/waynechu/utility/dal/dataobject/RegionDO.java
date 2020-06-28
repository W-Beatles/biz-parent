package cn.waynechu.utility.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @author zhuwei
 * @date 2019-08-16 23:22
 */
@Data
@TableName(value = "region")
public class RegionDO implements Serializable {
    /**
     * 区域id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 父级区域id
     */
    @TableField(value = "pid")
    private Integer pid;

    /**
     * 区域等级
     */
    @TableField(value = "level")
    private Integer level;

    /**
     * 区域名称
     */
    @TableField(value = "region_name")
    private String regionName;

    /**
     * 地名简称
     */
    @TableField(value = "simple_name")
    private String simpleName;

    /**
     * 区域编码
     */
    @TableField(value = "city_code")
    private String cityCode;

    /**
     * 邮政编码
     */
    @TableField(value = "postal_code")
    private String postalCode;

    /**
     * 组合名称
     */
    @TableField(value = "merge_name")
    private String mergeName;

    /**
     * 经度
     */
    @TableField(value = "lng")
    private Double lng;

    /**
     * 纬度
     */
    @TableField(value = "lat")
    private Double lat;

    /**
     * 区域拼音
     */
    @TableField(value = "pinyin")
    private String pinyin;

    /**
     * 是否删除: 0否 1是 默认0
     */
    @TableField(value = "deleted_status")
    private Boolean deletedStatus;

    private static final long serialVersionUID = 1L;

    public static final String COL_PID = "pid";

    public static final String COL_LEVEL = "level";

    public static final String COL_REGION_NAME = "region_name";

    public static final String COL_SIMPLE_NAME = "simple_name";

    public static final String COL_CITY_CODE = "city_code";

    public static final String COL_POSTAL_CODE = "postal_code";

    public static final String COL_MERGE_NAME = "merge_name";

    public static final String COL_LNG = "lng";

    public static final String COL_LAT = "lat";

    public static final String COL_PINYIN = "pinyin";

    public static final String COL_DELETED_STATUS = "deleted_status";
}