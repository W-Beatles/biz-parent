package com.waynechu.renting.core.convert.dto;

import cn.waynechu.webcommon.util.BeanUtil;
import com.waynechu.renting.dal.common.entity.SysDictionary;
import com.waynechu.renting.facade.dto.SysDictionaryDTO;
import lombok.experimental.UtilityClass;

/**
 * @author zhuwei
 * @date 2019/1/18 15:53
 */
@UtilityClass
public class SysDictionaryDtoConvert {

    public static SysDictionary toSysDictionary(SysDictionaryDTO sysDictionaryDTO) {
        return BeanUtil.beanTransfer(sysDictionaryDTO, SysDictionary.class);
    }
}
