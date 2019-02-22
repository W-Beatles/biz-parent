package cn.waynechu.renting.facade.service;

import cn.waynechu.renting.facade.dto.ShopConfigLogDTO;

import java.util.Date;
import java.util.List;

/**
 * @author zhuwei
 * @date 2019/2/22 14:21
 */
public interface ShopConfigService {

    /**
     * 导出变更日志
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 变更日志
     */
    List<ShopConfigLogDTO> exportLog(Date startDate, Date endDate);
}
