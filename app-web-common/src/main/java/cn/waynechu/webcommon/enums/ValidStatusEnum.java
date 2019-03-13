package cn.waynechu.webcommon.enums;

/**
 * @author zhuwei
 * @date 2018/11/6 17:24
 */
public enum ValidStatusEnum implements BaseEnum {
    /**
     * 通用 有效/无效 状态枚举
     */
    INVALID(0, "无效"),
    VALID(1, "有效");

    private int code;
    private String desc;

    ValidStatusEnum(int code, String desc) {
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
        for (ValidStatusEnum validStatusEnum : values()) {
            if (validStatusEnum.getCode() == code) {
                return validStatusEnum;
            }
        }
        return null;
    }
}
