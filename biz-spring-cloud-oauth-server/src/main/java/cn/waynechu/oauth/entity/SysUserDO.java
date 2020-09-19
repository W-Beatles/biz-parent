package cn.waynechu.oauth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @author zhuwei
 * @since 2020/9/19 17:58
 */
@Data
@TableName(value = "sys_user")
public class SysUserDO implements Serializable {
    public static final String COL_USER_ACCOUNT = "user_account";
    public static final String COL_USER_PASSWORD = "user_password";
    public static final String COL_SALT = "salt";
    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户姓
     */
    @TableField(value = "username")
    private String username;

    /**
     * 用户昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 头像地址
     */
    @TableField(value = "head_img_url")
    private String headImgUrl;

    /**
     * 性别: 0未知的性别 1男 2女 5女性改为男性 6男性改为女性
     */
    @TableField(value = "sex")
    private Boolean sex;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 手机号
     */
    @TableField(value = "mobile")
    private String mobile;

    /**
     * 身份证号
     */
    @TableField(value = "id_card")
    private String idCard;

    /**
     * 用户状态: 0启用 1禁用
     */
    @TableField(value = "user_status")
    private Integer userStatus;

    /**
     * 创建人
     */
    @TableField(value = "created_user")
    private String createdUser;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField(value = "updated_user")
    private String updatedUser;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    private Date updatedTime;

    /**
     * 是否删除: 0否 1是
     */
    @TableField(value = "deleted_status")
    private Boolean deletedStatus;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_USERNAME = "username";

    public static final String COL_NICKNAME = "nickname";

    public static final String COL_PASSWORD = "password";

    public static final String COL_HEAD_IMG_URL = "head_img_url";

    public static final String COL_SEX = "sex";

    public static final String COL_EMAIL = "email";

    public static final String COL_MOBILE = "mobile";

    public static final String COL_ID_CARD = "id_card";

    public static final String COL_USER_STATUS = "user_status";

    public static final String COL_CREATED_USER = "created_user";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_USER = "updated_user";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DELETED_STATUS = "deleted_status";
}