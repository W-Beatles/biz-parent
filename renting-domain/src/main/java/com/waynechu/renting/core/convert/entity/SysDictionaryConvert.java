package com.waynechu.renting.core.convert.entity;

import cn.waynechu.springcloud.common.util.BeanUtil;
import com.waynechu.renting.dal.common.entity.SysDictionary;
import com.waynechu.renting.facade.dto.SysDictionaryDTO;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * @author zhuwei
 * @date 2019/1/18 14:22
 */
@UtilityClass
public class SysDictionaryConvert {

    public static SysDictionaryDTO toSysDictionaryDTO(SysDictionary sysDictionary) {
        return BeanUtil.beanTransfer(sysDictionary, SysDictionaryDTO.class);
    }

    public static List<SysDictionaryDTO> toSysDictionaryDTOList(List<SysDictionary> sysDictionaryList) {
        return BeanUtil.beanListTransfer(sysDictionaryList, SysDictionaryDTO.class);
    }
}
