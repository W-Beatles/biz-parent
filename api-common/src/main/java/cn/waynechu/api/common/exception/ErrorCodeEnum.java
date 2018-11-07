package cn.waynechu.api.common.exception;

import cn.waynechu.api.common.enums.BaseEnum;

/**
 * @author zhuwei
 * @date 2018/11/6 18:49
 */
public enum ErrorCodeEnum implements BaseEnum {
    /**
     * 响应枚举
     */
    SUCCESS(10000, "success", "操作成功"),
    SYSTEM_ERROR(10002, "SYSTEM_ERROR", "系统异常"),

    ILLEGAL_ARGUMENT(10010, "ILLEGAL_ARGUMENT", "参数错误"),
    MISSING_REQUEST_PARAMETER(10011, "MISSING_REQUEST_PARAMETER", "缺少请求参数");

    private int code;
    private String name;
    private String desc;

    ErrorCodeEnum(int code, String name, String desc) {
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

    @Override
    public BaseEnum codeOf(int code) {
        for (ErrorCodeEnum errorCodeEnum : values()) {
            if (errorCodeEnum.getCode() == code) {
                return errorCodeEnum;
            }
        }
        return null;
    }

    @Override
    public BaseEnum nameOf(String name) {
        for (ErrorCodeEnum errorCodeEnum : values()) {
            if (errorCodeEnum.getName().equals(name)) {
                return errorCodeEnum;
            }
        }
        return null;
    }
}
