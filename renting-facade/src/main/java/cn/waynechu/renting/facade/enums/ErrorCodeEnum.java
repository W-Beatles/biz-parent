package cn.waynechu.renting.facade.enums;

import cn.waynechu.common.enums.IBaseEnum;

/**
 * @author zhuwei
 * @date 2018/12/24 9:35
 */
public enum ErrorCodeEnum implements IBaseEnum {
    /**
     * 错误码
     */
    HOUSE_NOT_EXIST(11000, "HOUSE_NOT_EXIST", "房源不存在");

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
    public IBaseEnum getByCode(int code) {
        for (ErrorCodeEnum errorCodeEnum : values()) {
            if (errorCodeEnum.getCode() == code) {
                return errorCodeEnum;
            }
        }
        return null;
    }

    @Override
    public IBaseEnum getByName(String name) {
        for (ErrorCodeEnum errorCodeEnum : values()) {
            if (errorCodeEnum.getName().equals(name)) {
                return errorCodeEnum;
            }
        }
        return null;
    }
}
