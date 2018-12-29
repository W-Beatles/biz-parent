package cn.waynechu.renting.web.convert;

import cn.waynechu.common.bean.BeanUtil;
import cn.waynechu.renting.facade.dto.HouseDTO;
import cn.waynechu.renting.facade.request.HouseCreateReq;
import cn.waynechu.renting.facade.vo.HouseVO;

import java.util.List;

/**
 * @author zhuwei
 * @date 2018/12/29 9:56
 */
public class HouseConvert {
    private HouseConvert() {
    }

    public static HouseVO convertHouseVO(HouseDTO houseDTO) {
        return BeanUtil.beanTransfer(houseDTO, HouseVO.class);
    }

    public static HouseDTO convertHouseDTO(HouseCreateReq houseCreateReq) {
        return BeanUtil.beanTransfer(houseCreateReq, HouseDTO.class);
    }

    public static List<HouseVO> convertHouseVOS(List<HouseDTO> list) {
        return BeanUtil.beanListTransfer(list, HouseVO.class);
    }
}
