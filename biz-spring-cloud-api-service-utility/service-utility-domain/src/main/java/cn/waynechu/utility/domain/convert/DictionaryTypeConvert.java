package cn.waynechu.utility.domain.convert;

import cn.waynechu.springcloud.common.util.CollectionUtil;
import cn.waynechu.utility.dal.dataobject.DictionaryTypeDO;
import cn.waynechu.utility.facade.response.DicTypeResponse;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuwei
 * @since 2020/7/3 17:54
 */
@UtilityClass
public class DictionaryTypeConvert {

    public static List<DicTypeResponse> toDicTypeResponseList(List<DictionaryTypeDO> dictionaryTypeDOList) {
        List<DicTypeResponse> returnValue = new ArrayList<>();

        if (CollectionUtil.isNotNullOrEmpty(dictionaryTypeDOList)) {
            DicTypeResponse dicTypeResponse;
            for (DictionaryTypeDO typeDO : dictionaryTypeDOList) {
                dicTypeResponse = new DicTypeResponse();
                BeanUtils.copyProperties(typeDO, dicTypeResponse);
                returnValue.add(dicTypeResponse);
            }
        }
        return returnValue;
    }
}
