package cn.waynechu.renting.core.service.impl;

import cn.waynechu.renting.core.service.HouseService;
import cn.waynechu.renting.dal.entity.House;
import cn.waynechu.renting.dal.mapper.HouseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhuwei
 * @date 2018/11/14 16:33
 */
@Slf4j
@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Override
    public House getById(Long id) {
        return houseMapper.selectByPrimaryKey(id);
    }
}
