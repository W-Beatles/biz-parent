package cn.waynechu.archetype.portal.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhuwei
 * @since 2020-06-21 00:07
 */
@Data
@TableName(value = "archetype")
public class ArchetypeDO implements Serializable {
    /**
     * 原型id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * AppID
     */
    @TableField(value = "app_id")
    private String appId;

    /**
     * 项目名称
     */
    @TableField(value = "app_name")
    private String appName;

    /**
     * 项目类型: 0Service 1SDK
     */
    @TableField(value = "app_type")
    private Integer appType;

    /**
     * 包名
     */
    @TableField(value = "package_name")
    private String packageName;

    /**
     * 项目描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 状态: 0生成中 1成功 2失败
     */
    @TableField(value = "status_code")
    private Integer statusCode;

    /**
     * 上传git: 0否 1是
     */
    @TableField(value = "git_upload_type")
    private Boolean gitUploadType;

    /**
     * git仓库地址
     */
    @TableField(value = "git_url")
    private String gitUrl;

    /**
     * 原型下载地址
     */
    @TableField(value = "download_url")
    private String downloadUrl;

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

    public static final String COL_APP_ID = "app_id";

    public static final String COL_APP_NAME = "app_name";

    public static final String COL_APP_TYPE = "app_type";

    public static final String COL_PACKAGE_NAME = "package_name";

    public static final String COL_DESCRIPTION = "description";

    public static final String COL_STATUS_CODE = "status_code";

    public static final String COL_GIT_UPLOAD_TYPE = "git_upload_type";

    public static final String COL_GIT_URL = "git_url";

    public static final String COL_DOWNLOAD_URL = "download_url";

    public static final String COL_CREATED_USER = "created_user";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_USER = "updated_user";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DELETED_STATUS = "deleted_status";
}