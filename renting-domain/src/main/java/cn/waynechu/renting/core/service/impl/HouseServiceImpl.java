package cn.waynechu.renting.core.service.impl;

import cn.waynechu.common.bean.BeanUtil;
import cn.waynechu.renting.core.service.HouseService;
import cn.waynechu.renting.dal.entity.House;
import cn.waynechu.renting.dal.entity.HouseExample;
import cn.waynechu.renting.dal.mapper.HouseMapper;
import cn.waynechu.renting.facade.model.ModelHouse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public ModelHouse getById(Long id) {
        return BeanUtil.beanTransfer(houseMapper.selectByPrimaryKey(id), ModelHouse.class);
    }

    @Override
    public boolean create(House house) {
        return houseMapper.insertSelective(house) > 0;
    }

    @Override
    public boolean update(House house) {
        return houseMapper.updateByPrimaryKeySelective(house) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        House house = new House();
        house.setId(id);
        house.setIsDeleted(true);
        return houseMapper.updateByPrimaryKeySelective(house) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean copyByIdTransition(Long id) {
        House selectHouse = houseMapper.selectByPrimaryKey(id);
        selectHouse.setId(null);
        return houseMapper.insert(selectHouse) > 0;
    }

    @Override
    public PageInfo<House> search(House house, Integer pageNum, Integer pageSize) {
        HouseExample example = BeanUtil.beanTransfer(house, HouseExample.class);

        PageHelper.startPage(pageNum, pageSize);
        List<House> houses = houseMapper.selectByExample(example);
        return new PageInfo<>(houses);
    }
}