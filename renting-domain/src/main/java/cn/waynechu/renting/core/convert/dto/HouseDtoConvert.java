package cn.waynechu.renting.core.convert.dto;

import cn.waynechu.renting.dal.renting.entity.House;
import cn.waynechu.renting.facade.dto.HouseDTO;
import cn.waynechu.webcommon.util.BeanUtil;
import lombok.experimental.UtilityClass;

/**
 * @author zhuwei
 * @date 2019/1/18 16:05
 */
@UtilityClass
public class HouseDtoConvert {

    public static House toHouse(HouseDTO houseDTO) {
        return BeanUtil.beanTransfer(houseDTO, House.class);
    }
}
