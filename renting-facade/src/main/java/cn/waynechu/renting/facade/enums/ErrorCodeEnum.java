package cn.waynechu.renting.facade.enums;

import cn.waynechu.webcommon.enums.BaseEnum;

import java.io.Serializable;

/**
 * @author zhuwei
 * @date 2018/12/24 9:35
 */
public enum ErrorCodeEnum implements BaseEnum, Serializable {
    /**
     * 错误码
     */
    HOUSE_NOT_EXIST(11000, "房源不存在");

    private int code;
    private String desc;

    ErrorCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public BaseEnum getByCode(int code) {
        for (ErrorCodeEnum errorCodeEnum : values()) {
            if (errorCodeEnum.getCode() == code) {
                return errorCodeEnum;
            }
        }
        return null;
    }
}
