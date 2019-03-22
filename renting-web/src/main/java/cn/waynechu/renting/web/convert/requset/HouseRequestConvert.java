package cn.waynechu.renting.web.convert.requset;

import cn.waynechu.renting.facade.dto.HouseDTO;
import cn.waynechu.renting.web.request.HouseCreateRequest;
import cn.waynechu.renting.web.request.HouseSearchRequest;
import cn.waynechu.renting.web.request.HouseUpdateRequest;
import cn.waynechu.webcommon.util.BeanUtil;
import lombok.experimental.UtilityClass;

/**
 * @author zhuwei
 * @date 2019/1/18 14:51
 */
@UtilityClass
public class HouseRequestConvert {

    public static HouseDTO toHouseDTO(HouseCreateRequest houseCreateRequest) {
        return BeanUtil.beanTransfer(houseCreateRequest, HouseDTO.class);
    }

    public static HouseDTO toHouseDTO(HouseUpdateRequest houseUpdateRequest) {
        return BeanUtil.beanTransfer(houseUpdateRequest, HouseDTO.class);
    }

    public static HouseDTO toHouseDTO(HouseSearchRequest houseSearchRequest) {
        return BeanUtil.beanTransfer(houseSearchRequest, HouseDTO.class);
    }
}
