package cn.waynechu.renting.core.convert.entity;

import cn.waynechu.renting.dal.common.entity.ShopConfigLog;
import cn.waynechu.renting.facade.dto.ShopConfigLogDTO;
import cn.waynechu.webcommon.util.BeanUtil;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * @author zhuwei
 * @date 2019/2/22 14:53
 */
@UtilityClass
public class ShopConfigLogConvert {

    public static List<ShopConfigLogDTO> toShopConfigLogDTO(List<ShopConfigLog> shopConfigLogs) {
        return BeanUtil.beanListTransfer(shopConfigLogs, ShopConfigLogDTO.class);
    }
}
