package com.tuhu.datasource.dynamic;

/**
 * @author zhuwei
 * @date 2018/11/22 15:28
 */
public enum RoutingPatternEnum {
    /**
     * 从库负载均衡方式，默认轮询
     */
    POLLING(0, "polling", "轮询"),
    RANDOM(1, "random", "随机"),;

    private int code;
    private String name;
    private String desc;

    RoutingPatternEnum(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getDesc() {
        return this.desc;
    }

    public static RoutingPatternEnum codeOf(int code) {
        for (RoutingPatternEnum resultEnum : values()) {
            if (resultEnum.getCode() == code) {
                return resultEnum;
            }
        }
        throw new IllegalArgumentException();
    }

    public static RoutingPatternEnum nameOf(String name) {
        for (RoutingPatternEnum resultEnum : values()) {
            if (resultEnum.getName().equals(name)) {
                return resultEnum;
            }
        }
        throw new IllegalArgumentException();
    }
}
