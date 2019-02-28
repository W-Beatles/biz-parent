package cn.waynechu.renting.web.convert.dto;

import cn.waynechu.renting.facade.dto.HouseDTO;
import cn.waynechu.renting.facade.model.ModelHouse;
import cn.waynechu.webcommon.util.BeanUtil;
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
