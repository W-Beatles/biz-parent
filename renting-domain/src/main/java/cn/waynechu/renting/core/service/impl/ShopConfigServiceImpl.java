package cn.waynechu.renting.core.service.impl;

import cn.waynechu.renting.core.convert.entity.ShopConfigLogConvert;
import cn.waynechu.renting.core.repository.ShopConfigLogRepository;
import cn.waynechu.renting.dal.common.entity.ShopConfigLog;
import cn.waynechu.renting.facade.dto.ShopConfigLogDTO;
import cn.waynechu.renting.facade.service.ShopConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zhuwei
 * @date 2019/2/22 14:27
 */
@Slf4j
@Service("shopConfigService")
public class ShopConfigServiceImpl implements ShopConfigService {

    @Autowired
    private ShopConfigLogRepository shopConfigLogRepository;

    @Override
    public List<ShopConfigLogDTO> exportLog(Date startDate, Date endDate) {
        List<ShopConfigLog> shopConfigLogs = shopConfigLogRepository.getByTime(startDate, endDate);
        return ShopConfigLogConvert.toShopConfigLogDTO(shopConfigLogs);
    }
}
