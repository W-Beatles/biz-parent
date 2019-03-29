package com.waynechu.renting.web.convert;

import cn.waynechu.webcommon.util.BeanUtil;
import com.waynechu.renting.facade.dto.HouseDTO;
import com.waynechu.renting.web.model.ModelHouse;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * @author zhuwei
 * @date 2018/12/29 10:45
 */
@UtilityClass
public class HouseDtoConvert {

    public static ModelHouse toHouseResp(HouseDTO houseDTO) {
        return BeanUtil.beanTransfer(houseDTO, ModelHouse.class);
    }

    public static List<ModelHouse> toHouseRespList(List<HouseDTO> list) {
        return BeanUtil.beanListTransfer(list, ModelHouse.class);
    }
}
