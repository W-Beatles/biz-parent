package cn.waynechu.renting.web.convert.requset;

import cn.waynechu.renting.facade.dto.HouseDTO;
import cn.waynechu.renting.facade.request.HouseCreateReq;
import cn.waynechu.renting.facade.request.HouseSearchReq;
import cn.waynechu.renting.facade.request.HouseUpdateReq;
import cn.waynechu.webcommon.util.BeanUtil;
import lombok.experimental.UtilityClass;

/**
 * @author zhuwei
 * @date 2019/1/18 14:51
 */
@UtilityClass
public class HouseRequestConvert {

    public static HouseDTO toHouseDTO(HouseCreateReq houseCreateReq) {
        return BeanUtil.beanTransfer(houseCreateReq, HouseDTO.class);
    }

    public static HouseDTO toHouseDTO(HouseUpdateReq houseUpdateReq) {
        return BeanUtil.beanTransfer(houseUpdateReq, HouseDTO.class);
    }

    public static HouseDTO toHouseDTO(HouseSearchReq houseSearchReq) {
        return BeanUtil.beanTransfer(houseSearchReq, HouseDTO.class);
    }
}
