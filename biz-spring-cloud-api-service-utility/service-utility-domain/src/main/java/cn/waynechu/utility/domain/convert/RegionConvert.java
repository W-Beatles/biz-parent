package cn.waynechu.utility.domain.convert;

import cn.waynechu.springcloud.common.util.BeanUtil;
import cn.waynechu.springcloud.common.util.CollectionUtil;
import cn.waynechu.utility.dal.dataobject.RegionDO;
import cn.waynechu.utility.facade.response.RegionResponse;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuwei
 * @since 2019-08-11 16:47
 */
@UtilityClass
public class RegionConvert {

    public static List<RegionResponse> toRegionResponseList(List<RegionDO> regionDoList) {
        List<RegionResponse> returnValue = new ArrayList<>();
        if (CollectionUtil.isNotNullOrEmpty(regionDoList)) {
            RegionResponse response;
            for (RegionDO regionDo : regionDoList) {
                response = new RegionResponse();
                BeanUtil.copyProperties(regionDo, response);
                returnValue.add(response);
            }
        }
        return returnValue;
    }
}
