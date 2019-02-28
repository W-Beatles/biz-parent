package cn.waynechu.renting.core.convert.entity;

import cn.waynechu.renting.dal.common.entity.SysDictionary;
import cn.waynechu.renting.facade.dto.SysDictionaryDTO;
import cn.waynechu.webcommon.util.BeanUtil;
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
