package cn.waynechu.facade.common.enums;

/**
 * @author zhuwei
 * @date 2018/11/6 17:31
 */
public interface AbstractEnum {

    /**
     * 获取枚举code
     *
     * @return code
     */
    int getCode();

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
    AbstractEnum getByCode(int code);
}
