package com.waynechu.renting.core.convert.dto;

import cn.waynechu.springcloud.common.util.BeanUtil;
import com.waynechu.renting.dal.renting.entity.House;
import com.waynechu.renting.facade.dto.HouseDTO;
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
