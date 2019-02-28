package cn.waynechu.renting.core.convert.dto;

import cn.waynechu.renting.dal.common.entity.SysDictionary;
import cn.waynechu.renting.facade.dto.SysDictionaryDTO;
import cn.waynechu.webcommon.util.BeanUtil;
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
