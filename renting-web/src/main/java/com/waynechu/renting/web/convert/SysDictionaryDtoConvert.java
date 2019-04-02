package com.waynechu.renting.web.convert;

import cn.waynechu.springcloud.common.util.BeanUtil;
import com.waynechu.renting.facade.dto.SysDictionaryDTO;
import com.waynechu.renting.web.response.SysDictionaryResponse;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * @author zhuwei
 * @date 2019/1/18 14:47
 */
@UtilityClass
public class SysDictionaryDtoConvert {

    public static SysDictionaryResponse toSysDictionaryResp(SysDictionaryDTO sysDictionaryDTO) {
        return BeanUtil.beanTransfer(sysDictionaryDTO, SysDictionaryResponse.class);
    }

    public static List<SysDictionaryResponse> toSysDictionaryRespList(List<SysDictionaryDTO> list) {
        return BeanUtil.beanListTransfer(list, SysDictionaryResponse.class);
    }
}
