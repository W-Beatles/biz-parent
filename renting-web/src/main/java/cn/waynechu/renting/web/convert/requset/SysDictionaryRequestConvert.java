package cn.waynechu.renting.web.convert.requset;

import cn.waynechu.renting.facade.dto.SysDictionaryDTO;
import cn.waynechu.renting.facade.request.SysDictionaryCreateRequest;
import cn.waynechu.renting.facade.request.SysDictionarySearchRequest;
import cn.waynechu.renting.facade.request.SysDictionaryUpdateRequest;
import cn.waynechu.webcommon.util.BeanUtil;
import lombok.experimental.UtilityClass;

/**
 * @author zhuwei
 * @date 2019/1/18 15:14
 */
@UtilityClass
public class SysDictionaryRequestConvert {

    public static SysDictionaryDTO toSysDictionaryDTO(SysDictionaryCreateRequest sysDictionaryCreateReq) {
        return BeanUtil.beanTransfer(sysDictionaryCreateReq, SysDictionaryDTO.class);
    }

    public static SysDictionaryDTO toSysDictionaryDTO(SysDictionaryUpdateRequest sysDictionaryUpdateRequest) {
        return BeanUtil.beanTransfer(sysDictionaryUpdateRequest, SysDictionaryDTO.class);
    }

    public static SysDictionaryDTO toSysDictionaryDTO(SysDictionarySearchRequest sysDictionarySearchRequest) {
        return BeanUtil.beanTransfer(sysDictionarySearchRequest, SysDictionaryDTO.class);
    }
}
