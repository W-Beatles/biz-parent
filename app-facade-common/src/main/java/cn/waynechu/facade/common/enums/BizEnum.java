package cn.waynechu.facade.common.enums;

import java.io.Serializable;

/**
 * 业务异常枚举接口定义
 * <p>
 * 实现该接口可创建自定义业务异常
 *
 * @author zhuwei
 * @date 2018/11/6 17:31
 */
public interface BizEnum extends Serializable {
    /**
     * 获取枚举code
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
}
