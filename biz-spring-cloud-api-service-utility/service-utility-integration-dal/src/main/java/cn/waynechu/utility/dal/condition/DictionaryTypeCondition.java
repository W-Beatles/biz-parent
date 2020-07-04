package cn.waynechu.utility.dal.condition;

import cn.waynechu.utility.dal.dataobject.DictionaryTypeDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhuwei
 * @since 2020/7/3 17:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictionaryTypeCondition extends DictionaryTypeDO {

    /**
     * 类型编码
     */
    private String typeCodeLike;

    /**
     * 所属AppID
     */
    private String appIdLike;
}
