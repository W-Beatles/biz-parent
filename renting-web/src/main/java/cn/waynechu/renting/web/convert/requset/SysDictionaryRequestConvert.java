package cn.waynechu.renting.web.convert.requset;

import cn.waynechu.renting.facade.dto.SysDictionaryDTO;
import cn.waynechu.renting.facade.request.SysDictionaryCreateReq;
import cn.waynechu.renting.facade.request.SysDictionarySearchReq;
import cn.waynechu.renting.facade.request.SysDictionaryUpdateReq;
import cn.waynechu.webcommon.util.BeanUtil;
import lombok.experimental.UtilityClass;

/**
 * @author zhuwei
 * @date 2019/1/18 15:14
 */
@UtilityClass
public class SysDictionaryRequestConvert {

    public static SysDictionaryDTO toSysDictionaryDTO(SysDictionaryCreateReq sysDictionaryCreateReq) {
        return BeanUtil.beanTransfer(sysDictionaryCreateReq, SysDictionaryDTO.class);
    }

    public static SysDictionaryDTO toSysDictionaryDTO(SysDictionaryUpdateReq sysDictionaryUpdateReq) {
        return BeanUtil.beanTransfer(sysDictionaryUpdateReq, SysDictionaryDTO.class);
    }

    public static SysDictionaryDTO toSysDictionaryDTO(SysDictionarySearchReq sysDictionarySearchReq) {
        return BeanUtil.beanTransfer(sysDictionarySearchReq, SysDictionaryDTO.class);
    }
}
