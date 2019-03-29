package com.waynechu.renting.core.service.impl;

import com.waynechu.renting.core.convert.entity.ShopConfigLogConvert;
import com.waynechu.renting.core.repository.ShopConfigLogRepository;
import com.waynechu.renting.dal.common.entity.ShopConfigLog;
import com.waynechu.renting.facade.dto.ShopConfigLogDTO;
import com.waynechu.renting.facade.service.ShopConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author zhuwei
 * @date 2019/2/22 14:27
 */
@Slf4j
@Service(version = "1.0.0")
public class ShopConfigServiceImpl implements ShopConfigService {

    @Autowired
    private ShopConfigLogRepository shopConfigLogRepository;

    @Override
    public List<ShopConfigLogDTO> exportLog(Date startDate, Date endDate) {
        List<ShopConfigLog> shopConfigLogs = shopConfigLogRepository.getByTime(startDate, endDate);
        return ShopConfigLogConvert.toShopConfigLogDTO(shopConfigLogs);
    }
}
