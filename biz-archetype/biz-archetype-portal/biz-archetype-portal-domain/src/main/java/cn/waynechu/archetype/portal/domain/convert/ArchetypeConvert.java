package cn.waynechu.archetype.portal.domain.convert;

import cn.waynechu.archetype.portal.dal.dataobject.ArchetypeDO;
import cn.waynechu.archetype.portal.facade.response.SearchArchetypeResponse;
import cn.waynechu.springcloud.common.util.BeanUtil;
import cn.waynechu.springcloud.common.util.CollectionUtil;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;

/**
 * @author zhuwei
 * @date 2020-06-20 21:41
 */
@UtilityClass
public class ArchetypeConvert {

    public static List<SearchArchetypeResponse> toSearchArchetypeResponse(List<ArchetypeDO> archetypeDOList) {
        if (CollectionUtil.isNullOrEmpty(archetypeDOList)) {
            return Collections.emptyList();
        }
        return BeanUtil.beanListTransfer(archetypeDOList, SearchArchetypeResponse.class);
    }
}
