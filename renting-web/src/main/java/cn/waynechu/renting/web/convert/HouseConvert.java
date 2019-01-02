package cn.waynechu.renting.web.convert;

import cn.waynechu.webcommon.bean.BeanUtil;
import cn.waynechu.renting.facade.dto.HouseDTO;
import cn.waynechu.renting.facade.request.HouseCreateRequest;
import cn.waynechu.renting.facade.vo.HouseResponse;

import java.util.List;

/**
 * @author zhuwei
 * @date 2018/12/29 9:56
 */
public class HouseConvert {
    private HouseConvert() {
    }

    public static HouseResponse convertHouseVO(HouseDTO houseDTO) {
        return BeanUtil.beanTransfer(houseDTO, HouseResponse.class);
    }

    public static HouseDTO convertHouseDTO(HouseCreateRequest houseCreateReq) {
        return BeanUtil.beanTransfer(houseCreateReq, HouseDTO.class);
    }

    public static List<HouseResponse> convertHouseVOS(List<HouseDTO> list) {
        return BeanUtil.beanListTransfer(list, HouseResponse.class);
    }
}
