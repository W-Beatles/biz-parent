package cn.waynechu.archetype.portal.dal.condition;

import lombok.Data;

/**
 * @author zhuwei
 * @since 2020-06-20 21:39
 */
@Data
public class ListArchetypeCondition {

    /**
     * 原型id
     */
    private Long id;

    /**
     * AppID
     */
    private String appId;

    /**
     * 项目名称
     */
    private String appName;

    /**
     * 排序字段
     */
    private String orderBy;
}
