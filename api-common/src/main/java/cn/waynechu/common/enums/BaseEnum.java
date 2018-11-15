package cn.waynechu.common.enums;

/**
 * @author zhuwei
 * @date 2018/11/6 17:31
 */
public interface BaseEnum {
    /**
     * 获取枚举值
     *
     * @return code
     */
    int getCode();

    /**
     * 获取枚举名称
     *
     * @return name
     */
    String getName();

    /**
     * 获取枚举描述信息
     *
     * @return desc
     */
    String getDesc();

    /**
     * 通过code获取枚举对象
     *
     * @param code code
     * @return 枚举对象
     */
    BaseEnum codeOf(int code);

    /**
     * 通过name获取枚举对象
     *
     * @param name name
     * @return 枚举对象
     */
    BaseEnum nameOf(String name);
}
