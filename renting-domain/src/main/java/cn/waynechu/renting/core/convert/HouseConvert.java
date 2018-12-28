package cn.waynechu.renting.core.convert;

import cn.waynechu.common.bean.BeanUtil;
import cn.waynechu.renting.dal.entity.House;
import cn.waynechu.renting.facade.dto.HouseDTO;

/**
 * @author zhuwei
 * @date 2018/12/28 20:15
 */
public class HouseConvert {
    private HouseConvert(){}

    public static HouseDTO convertHouseDTO(House house) {
        return BeanUtil.beanTransfer(house, HouseDTO.class);
    }
}
