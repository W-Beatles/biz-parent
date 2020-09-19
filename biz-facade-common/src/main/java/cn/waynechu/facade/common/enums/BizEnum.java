package cn.waynechu.facade.common.enums;

import java.io.Serializable;

/**
 * 枚举基类
 *
 * @author zhuwei
 * @since 2018/11/6 17:31
 */
public interface BizEnum extends Serializable {

    /**
     * 获取枚举code
     *
     * @return code
     */
    Integer getCode();

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
}
