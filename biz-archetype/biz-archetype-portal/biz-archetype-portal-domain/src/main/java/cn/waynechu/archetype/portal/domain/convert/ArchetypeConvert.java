package cn.waynechu.archetype.portal.domain.convert;

import cn.waynechu.archetype.portal.common.enums.AppTypeEnum;
import cn.waynechu.archetype.portal.common.enums.StatusCodeEnum;
import cn.waynechu.archetype.portal.dal.dataobject.ArchetypeDO;
import cn.waynechu.archetype.portal.facade.response.SearchArchetypeResponse;
import cn.waynechu.springcloud.common.util.BeanUtil;
import cn.waynechu.springcloud.common.util.CollectionUtil;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author zhuwei
 * @date 2020-06-20 21:41
 */
@UtilityClass
public class ArchetypeConvert {

    public static List<SearchArchetypeResponse> toSearchArchetypeResponse(List<ArchetypeDO> archetypeDOList) {
        List<SearchArchetypeResponse> returnValue = new ArrayList<>();

        if (CollectionUtil.isNotNullOrEmpty(archetypeDOList)) {
            SearchArchetypeResponse response;
            for (ArchetypeDO archetypeDO : archetypeDOList) {
                response = new SearchArchetypeResponse();
                BeanUtil.copyProperties(archetypeDO, response);
                response.setAppTypeDesc(
                        Optional.ofNullable(AppTypeEnum.getByCode(archetypeDO.getAppType()))
                                .map(AppTypeEnum::getName).orElse(""));
                response.setStatusCodeDesc(Optional.ofNullable(StatusCodeEnum.getByCode(archetypeDO.getStatusCode()))
                        .map(StatusCodeEnum::getDesc).orElse(""));
                returnValue.add(response);
            }
        }
        return returnValue;
    }
}
