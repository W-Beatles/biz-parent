package com.waynechu.renting.web.convert;

import cn.waynechu.springcloud.common.util.BeanUtil;
import com.waynechu.renting.facade.dto.HouseDTO;
import com.waynechu.renting.web.response.HouseBizResponse;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * @author zhuwei
 * @date 2018/12/29 10:45
 */
@UtilityClass
public class HouseDtoConvert {

    public static HouseBizResponse toHouseResp(HouseDTO houseDTO) {
        return BeanUtil.beanTransfer(houseDTO, HouseBizResponse.class);
    }

    public static List<HouseBizResponse> toHouseRespList(List<HouseDTO> list) {
        return BeanUtil.beanListTransfer(list, HouseBizResponse.class);
    }
}
