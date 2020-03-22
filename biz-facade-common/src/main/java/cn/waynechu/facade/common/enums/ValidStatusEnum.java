package cn.waynechu.facade.common.enums;

import cn.waynechu.facade.common.exception.BizException;

import java.util.Objects;

/**
 * @author zhuwei
 * @date 2018/11/6 17:24
 */
public enum ValidStatusEnum implements BizEnum {
    /**
     * 通用 有效/无效 状态枚举
     */
    INVALID(0, "INVALID", "无效"),
    VALID(1, "VALID", "有效");

    private Integer code;
    private String name;
    private String desc;

    ValidStatusEnum(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
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

    public ValidStatusEnum getByCode(Integer code) {
        for (ValidStatusEnum validStatusEnum : values()) {
            if (Objects.equals(validStatusEnum.getCode(), code)) {
                return validStatusEnum;
            }
        }
        throw new BizException(BizErrorCodeEnum.INVALID_ENUM_CODE);
    }
}
