package com.waynechu.renting.web.convert;

import cn.waynechu.spirngcloud.common.util.BeanUtil;
import com.waynechu.renting.facade.dto.HouseDTO;
import com.waynechu.renting.web.response.HouseResponse;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * @author zhuwei
 * @date 2018/12/29 10:45
 */
@UtilityClass
public class HouseDtoConvert {

    public static HouseResponse toHouseResp(HouseDTO houseDTO) {
        return BeanUtil.beanTransfer(houseDTO, HouseResponse.class);
    }

    public static List<HouseResponse> toHouseRespList(List<HouseDTO> list) {
        return BeanUtil.beanListTransfer(list, HouseResponse.class);
    }
}
