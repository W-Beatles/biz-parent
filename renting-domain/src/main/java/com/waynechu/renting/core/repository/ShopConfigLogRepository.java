package com.waynechu.renting.core.repository;

import com.waynechu.renting.dal.common.entity.ShopConfigLog;
import com.waynechu.renting.dal.common.entity.ShopConfigLogExample;
import com.waynechu.renting.dal.common.mapper.ShopConfigLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author zhuwei
 * @date 2019/2/22 14:46
 */
@Repository
public class ShopConfigLogRepository {

    @Autowired
    private ShopConfigLogMapper shopConfigLogMapper;

    public List<ShopConfigLog> getByTime(Date startTime, Date endTime) {
        ShopConfigLogExample example = new ShopConfigLogExample();
        ShopConfigLogExample.Criteria criteria = example.createCriteria();
        criteria.andCreateTimeBetween(startTime, endTime);
        return shopConfigLogMapper.selectByExample(example);
    }
}
