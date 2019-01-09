package cn.waynechu.renting.core.convert;

import cn.waynechu.renting.dal.entity.House;
import cn.waynechu.renting.facade.dto.HouseDTO;
import cn.waynechu.webcommon.bean.BeanUtil;

import java.util.List;

/**
 * @author zhuwei
 * @date 2018/12/28 20:15
 */
public class HouseConvert {
    private HouseConvert() {
    }

    public static HouseDTO convertHouseDTO(House house) {
        return BeanUtil.beanTransfer(house, HouseDTO.class);
    }

    public static House convertHouse(HouseDTO houseDTO) {
        return BeanUtil.beanTransfer(houseDTO, House.class);
    }

    public static List<HouseDTO> convertHouseDTOList(List<House> houseList) {
        return BeanUtil.beanListTransfer(houseList, HouseDTO.class);
    }
}
