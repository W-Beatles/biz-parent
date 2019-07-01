package cn.waynechu.facade.common.enums;

/**
 * 响应枚举
 *
 * @author zhuwei
 * @date 2018/11/6 18:49
 */
public enum BizErrorCodeEnum implements BizEnum {
    /**
     * 预定义枚举: 10000~19999  自定义业务异常: 20000+
     */
    SUCCESS(10000, "SUCCESS", "操作成功"),

    // ERROR级别日志
    SYSTEM_ERROR(10002, "SYSTEM_ERROR", "系统异常"),
    CALL_SERVICE_ERROR(10003, "CALL_SERVICE_ERROR", "调用服务异常"),

    // 参数校验 10100~10199
    MISSING_REQUEST_PARAM(10101, "MISSING_REQUEST_PARAM", "缺少请求参数"),
    ARGUMENT_IS_INCORRECT(10102, "ARGUMENT_IS_INCORRECT", "请求参数格式不正确"),
    ARGUMENT_NOT_VALID(10103, "ARGUMENT_NOT_VALID", "请求参数校验不合法"),
    INVALID_ENUM_CODE(10104, "INVALID_ENUM_CODE", "无效枚举code值"),

    // 访问控制 10200~10299
    TOO_MANY_REQUEST(10200, "TOO_MANY_REQUEST", "访问过于频繁"),
    IP_LIMIT(10201, "IP_LIMIT", "IP限制"),
    MISSING_APP_KEY(10202, "MISSING_APP_KEY", "缺少AppKey参数"),
    INVALID_APP_KEY(10203, "INVALID_APP_KEY ", "非法的AppKey"),
    VERSION_ERROR(10204, "VERSION_ERROR", "版本号错误"),

    // 注册登录 10300~10399
    USERNAME_OR_PASSWORD_ERROR(10300, "USERNAME_OR_PASSWORD_ERROR", "用户名或者密码错误"),
    USER_DOES_NOT_EXISTS(10301, "USER_DOES_NOT_EXISTS", "用户不存在"),
    SUCCESS_ACCOUNT_NOT_EXIST(10302, "SUCCESS_ACCOUNT_NOT_EXIST", "账号不存在"),
    AUTHENTICATION_EXPIRED(10303, "AUTHENTICATION_EXPIRED", "身份认证过期"),
    LOGOUT_BY_LIMIT_STRATEGY(10304, "LOGOUT_BY_LIMIT_STRATEGY", "账号已被强制登出"),
    LOGIN_FAILED_FIVE_TIMES(10305, "LOGIN_FAILED_FIVE_TIMES", "密码错误5次，您的账号已经被封停30分钟"),
    LOGIN_FAILED_WITH_TIMES(10306, "LOGIN_FAILED_WITH_TIMES", "密码错误{0}次"),
    SMS_REQUEST_MORE_THAN_FIVE_TIMES(10307, "SMS_REQUEST_MORE_THAN_FIVE_TIMES", "短信验证码请求次数过多，请30分钟之后再获取"),
    CHANGE_DEVICE_MORE_THAN_FIVE_TIMES(10308, "CHANGE_DEVICE_MORE_THAN_FIVE_TIMES", "切换设备过于频繁，您的账号已经被封停30分钟"),
    DIFF_LAST_LOGIN_DEVICE(10309, "DIFF_LAST_LOGIN_DEVICE", "您的账号尝试在非常用设备登陆，请用使用短信验证码方式重新登陆"),
    PHONE_NUMBER_HAS_BEEN_USED(10310, "PHONE_NUMBER_HAS_BEEN_USED", "该手机号已经被使用"),

    // 应用授权 10400~10499
    INVALID_ACCESS_TOKEN(10400, "INVALID_ACCESS_TOKEN", "无效的AccessToken"),
    PERMISSION_DENY(10401, "PERMISSION_DENY", "用户服务无权限"),
    AUTH_FAILED(10402, "AUTH_FAILED", "授权失败"),
    INSUFFICIENT_USER_PERMISSIONS(10403, "INSUFFICIENT_USER_PERMISSIONS", "用户权限不足"),

    // 其它
    ILLEGAL_OPERATION(10500, "ILLEGAL_OPERATION", "非法操作"),
    OPERATION_FAILED(10501, "OPERATION_FAILED", "操作失败"),
    SUCCESS_EXIST(10502, "SUCCESS_EXIST", "数据已存在"),
    SUCCESS_NOT_EXIST(10503, "SUCCESS_NOT_EXIST", "数据不存在"),
    REPEAT_OPERATION(10504, "REPEAT_OPERATION", "重复操作"),
    ;

    private int code;
    private String name;
    private String desc;

    BizErrorCodeEnum(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    public BizErrorCodeEnum getByCode(int code) {
        for (BizErrorCodeEnum baseResultEnum : values()) {
            if (baseResultEnum.getCode() == code) {
                return baseResultEnum;
            }
        }
        return null;
    }
}
