package cn.waynechu.utility.dal.condition;

import cn.waynechu.utility.dal.dataobject.DictionaryDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhuwei
 * @since 2020/7/3 18:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictionaryCondition extends DictionaryDO {

    /**
     * 类型编码
     */
    private String dicTypeCodeLike;

    /**
     * 字典编码
     */
    private String dicCodeLike;

    /**
     * 字典描述
     */
    private String dicDescLike;

    /**
     * 排序
     */
    private String orderBy;
}
