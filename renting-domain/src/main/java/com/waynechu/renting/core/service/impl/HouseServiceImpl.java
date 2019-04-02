package com.waynechu.renting.core.service.impl;

import cn.waynechu.facade.common.page.PageInfo;
import cn.waynechu.springcloud.common.annotation.MethodLogAnnotation;
import cn.waynechu.springcloud.common.util.CollectionUtil;
import com.waynechu.renting.core.convert.dto.HouseDtoConvert;
import com.waynechu.renting.core.convert.entity.HouseConvert;
import com.waynechu.renting.core.repository.HouseRepository;
import com.waynechu.renting.core.repository.SysDictionaryRepository;
import com.waynechu.renting.dal.common.entity.SysDictionary;
import com.waynechu.renting.dal.renting.entity.House;
import com.waynechu.renting.facade.dto.HouseDTO;
import com.waynechu.renting.facade.dto.condition.HouseSearchCondition;
import com.waynechu.renting.facade.service.HouseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhuwei
 * @date 2018/11/14 16:33
 */
@Slf4j
@Service(version = "1.0.0", validation = "true")
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private SysDictionaryRepository sysDictionaryRepository;

    @MethodLogAnnotation(isClassFullName = true)
    @Override
    public HouseDTO getById(Long id) {
        HouseDTO returnValue = null;

        House house = houseRepository.getById(id);
        if (house != null) {
            returnValue = HouseConvert.toHouseDTO(house);
        }
        return returnValue;
    }

    @Override
    public boolean create(HouseDTO houseDTO) {
        boolean returnValue = false;

        House house = HouseDtoConvert.toHouse(houseDTO);
        if (house != null) {
            returnValue = houseRepository.create(house);
        }
        return returnValue;
    }

    @Override
    public boolean update(HouseDTO houseDTO) {
        boolean returnValue = false;

        House house = HouseDtoConvert.toHouse(houseDTO);
        if (house != null) {
            returnValue = houseRepository.updateSelective(house);
        }
        return returnValue;
    }

    @Override
    public boolean removeById(Long id) {
        return houseRepository.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean copyByIdTransition(Long id) {
        boolean returnValue = false;

        House copyHouse = houseRepository.getById(id);
        if (copyHouse != null) {
            copyHouse.setId(null);
            returnValue = houseRepository.create(copyHouse);
        }

        // TODO: 2019/1/18 不支持跨库事务，寻找分布式事务解决方案
        SysDictionary sysDictionary = sysDictionaryRepository.getById(1L);
        sysDictionary.setId(null);
        sysDictionaryRepository.create(sysDictionary);
        return returnValue;
    }

    @Override
    public PageInfo<HouseDTO> search(HouseSearchCondition condition) {
        PageInfo<HouseDTO> returnValue = null;

        if (condition != null) {
            PageInfo<House> housePageInfo = houseRepository.query(condition);

            if (CollectionUtil.isNotNullOrEmpty(housePageInfo.getList())) {
                List<HouseDTO> houseDTOS = HouseConvert.toHouseDTOList(housePageInfo.getList());
                returnValue = housePageInfo.replace(houseDTOS);
            }
        }
        return returnValue;
    }
}