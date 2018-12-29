package cn.waynechu.renting.web.convert.dto;

import cn.waynechu.common.bean.BeanUtil;
import cn.waynechu.renting.facade.dto.HouseDTO;
import cn.waynechu.renting.facade.request.HouseSearchReq;
import cn.waynechu.renting.facade.request.HouseUpdateReq;

/**
 * @author zhuwei
 * @date 2018/12/29 10:45
 */
public class HouseDtoConvert {
    public static HouseDTO convertHouseDTO(HouseUpdateReq houseUpdateReq) {
        return BeanUtil.beanTransfer(houseUpdateReq, HouseDTO.class);
    }

    public static HouseDTO convertHouseDTO(HouseSearchReq houseUpdateReq) {
        return BeanUtil.beanTransfer(houseUpdateReq, HouseDTO.class);
    }
}
