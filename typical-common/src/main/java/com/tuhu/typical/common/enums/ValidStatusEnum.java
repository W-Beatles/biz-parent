package com.tuhu.typical.common.enums;

/**
 * @author zhuwei
 * @date 2018/11/6 17:24
 */
public enum ValidStatusEnum implements BaseEnum {
    /**
     * 通用有效/无效状态枚举
     */
    VALID(0, "valid", "有效"),
    INVALID(1, "invalid", "无效");

    private int code;
    private String name;
    private String desc;

    ValidStatusEnum(int code, String name, String desc) {
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
        for (ValidStatusEnum validStatusEnum : values()) {
            if (validStatusEnum.getCode() == code) {
                return validStatusEnum;
            }
        }
        return null;
    }

    @Override
    public BaseEnum nameOf(String name) {
        for (ValidStatusEnum validStatusEnum : values()) {
            if (validStatusEnum.getName().equals(name)) {
                return validStatusEnum;
            }
        }
        return null;
    }
}
