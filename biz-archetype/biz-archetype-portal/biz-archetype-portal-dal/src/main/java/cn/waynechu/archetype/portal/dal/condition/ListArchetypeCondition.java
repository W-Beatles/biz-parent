package cn.waynechu.archetype.portal.dal.condition;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhuwei
 * @date 2020-06-20 21:39
 */
@Data
@Accessors(chain = true)
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

    /**
     * 是否升序
     */
    private Boolean isAsc = true;
}
