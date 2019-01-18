package cn.waynechu.renting.web.convert.dto;

import cn.waynechu.renting.facade.dto.SysDictionaryDTO;
import cn.waynechu.renting.facade.model.ModelSysDictionary;
import cn.waynechu.webcommon.bean.BeanUtil;
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
