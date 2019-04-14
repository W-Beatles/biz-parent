package com.waynechu.renting.facade.enums;

import cn.waynechu.facade.common.enums.BizEnum;

/**
 * @author zhuwei
 * @date 2018/12/24 9:35
 */
public enum RentingEnum implements BizEnum {

    /**
     * 错误码
     */
    HOUSE_NOT_EXIST(11000, "HOUSE_NOT_EXIST", "房源不存在");

    private int code;
    private String name;
    private String desc;

    RentingEnum(int code, String name, String desc) {
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

    public RentingEnum getByCode(int code) {
        for (RentingEnum rentingEnum : values()) {
            if (rentingEnum.getCode() == code) {
                return rentingEnum;
            }
        }
        return null;
    }
}
