package cn.waynechu.boot.starter.common.properties;

import lombok.Data;

/**
 * @author zhuwei
 * @date 2019/1/8 10:59
 */
@Data
public class DataModifiedProperties {

    /**
     * 是否开启数据库修改拦截器。默认关闭
     */
    private boolean enable = false;

    /**
     * 创建人字段属性名，默认createdUser
     */
    private String createdUserAttrName = "createdUser";

    /**
     * 创建时间字段属性名，默认createdTime
     */
    private String createdTimeAttrName = "createdTime";

    /**
     * 更新人字段属性名，默认updatedUser
     */
    private String updatedUserAttrName = "updatedUser";

    /**
     * 更新时间字段属性名，默认updatedTime
     */
    private String updatedTimeAttrName = "updatedTime";
}
