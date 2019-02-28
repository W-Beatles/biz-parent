package cn.waynechu.renting.core.convert.entity;

import cn.waynechu.renting.dal.renting.entity.House;
import cn.waynechu.renting.facade.dto.HouseDTO;
import cn.waynechu.webcommon.util.BeanUtil;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * @author zhuwei
 * @date 2018/12/28 20:15
 */
@UtilityClass
public class HouseConvert {

    public static HouseDTO toHouseDTO(House house) {
        return BeanUtil.beanTransfer(house, HouseDTO.class);
    }

    public static List<HouseDTO> toHouseDTOList(List<House> houseList) {
        return BeanUtil.beanListTransfer(houseList, HouseDTO.class);
    }
}
