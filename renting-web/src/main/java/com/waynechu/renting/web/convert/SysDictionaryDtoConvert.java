package com.waynechu.renting.web.convert;

import cn.waynechu.webcommon.util.BeanUtil;
import com.waynechu.renting.facade.dto.SysDictionaryDTO;
import com.waynechu.renting.web.model.ModelSysDictionary;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * @author zhuwei
 * @date 2019/1/18 14:47
 */
@UtilityClass
public class SysDictionaryDtoConvert {

    public static ModelSysDictionary toSysDictionaryResp(SysDictionaryDTO sysDictionaryDTO) {
        return BeanUtil.beanTransfer(sysDictionaryDTO, ModelSysDictionary.class);
    }

    public static List<ModelSysDictionary> toSysDictionaryRespList(List<SysDictionaryDTO> list) {
        return BeanUtil.beanListTransfer(list, ModelSysDictionary.class);
    }
}
