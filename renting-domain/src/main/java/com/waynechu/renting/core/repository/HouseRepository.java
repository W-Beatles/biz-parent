package com.waynechu.renting.core.repository;

import cn.waynechu.facade.common.page.PageInfo;
import com.alicp.jetcache.anno.*;
import com.github.pagehelper.PageHelper;
import com.waynechu.renting.core.constant.RedisPrefix;
import com.waynechu.renting.dal.renting.entity.House;
import com.waynechu.renting.dal.renting.entity.HouseExample;
import com.waynechu.renting.dal.renting.mapper.HouseMapper;
import com.waynechu.renting.facade.dto.condition.HouseSearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author zhuwei
 * @date 2018/12/28 16:57
 */
@Repository("houseRepository")
public class HouseRepository {

    @Autowired
    private HouseMapper houseMapper;

    @Cached(expire = 3600, name = RedisPrefix.REDIS_HOUSE_PREFIX, key = "#id", cacheType = CacheType.REMOTE)
    @CacheRefresh(refresh = 1800, stopRefreshAfterLastAccess = 3600)
    @CachePenetrationProtect
    public House getById(Long id) {
        HouseExample example = new HouseExample();
        HouseExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andIsDeletedEqualTo(false);
        List<House> houses = houseMapper.selectByExample(example);
        return houses.isEmpty() ? null : houses.get(0);
    }

    public boolean create(House house) {
        return houseMapper.insertSelective(house) > 0;
    }

    @CacheInvalidate(name = RedisPrefix.REDIS_HOUSE_PREFIX, key = "#house.id")
    public boolean updateSelective(House house) {
        HouseExample example = new HouseExample();
        HouseExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(house.getId());
        criteria.andIsDeletedEqualTo(false);
        return houseMapper.updateByExampleSelective(house, example) > 0;
    }

    @CacheInvalidate(name = RedisPrefix.REDIS_HOUSE_PREFIX, key = "#id")
    public boolean removeById(Long id) {
        HouseExample example = new HouseExample();
        HouseExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andIsDeletedEqualTo(false);

        House house = new House();
        house.setId(id);
        house.setIsDeleted(true);
        return houseMapper.updateByExampleSelective(house, example) > 0;
    }

    public PageInfo<House> query(HouseSearchCondition condition) {
        PageHelper.startPage(condition.getPageNum(), condition.getPageSize());

        HouseExample example = new HouseExample();
        HouseExample.Criteria criteria = example.createCriteria();
        if (condition.getId() != null) {
            criteria.andIdEqualTo(condition.getId());
        }
        if (StringUtils.hasText(condition.getTitle())) {
            criteria.andTitleEqualTo(condition.getTitle());
        }
        if (condition.getPrice() != null) {
            criteria.andPriceEqualTo(condition.getPrice());
        }
        criteria.andIsDeletedEqualTo(false);
        List<House> houses = houseMapper.selectByExample(example);
        return PageInfo.of(houses);
    }
}
