package cn.waynechu.renting.core.service.impl;

import cn.waynechu.boot.starter.common.util.RedisCache;
import cn.waynechu.renting.core.convert.HouseConvert;
import cn.waynechu.renting.core.repository.HouseRepository;
import cn.waynechu.renting.dal.entity.House;
import cn.waynechu.renting.facade.dto.HouseDTO;
import cn.waynechu.renting.facade.service.HouseService;
import cn.waynechu.webcommon.page.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhuwei
 * @date 2018/11/14 16:33
 */
@Slf4j
@Service("houseService")
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private RedisCache redisCache;

    @Override
    public HouseDTO getById(Long id) {
        HouseDTO returnValue = null;

        HouseDTO houseDTO = redisCache.get(String.valueOf(id), HouseDTO.class);
        if (houseDTO == null) {
            House house = houseRepository.getById(id);
            if (house != null) {
                returnValue = HouseConvert.convertHouseDTO(house);

                redisCache.set(String.valueOf(id), returnValue, 3600);
            }
        } else {
            return houseDTO;
        }
        return returnValue;
    }

    @Override
    public boolean create(HouseDTO houseDTO) {
        boolean returnValue = false;

        House house = HouseConvert.convertHouse(houseDTO);
        if (house != null) {
            returnValue = houseRepository.create(house);
        }
        return returnValue;
    }

    @Override
    public boolean update(HouseDTO houseDTO) {
        boolean returnValue = false;

        House house = HouseConvert.convertHouse(houseDTO);
        if (house != null) {
            returnValue = houseRepository.updateSelective(house);
        }
        return returnValue;
    }

    @Override
    public boolean removeById(Long id) {
        return houseRepository.removeById(id);
    }

    @Override
    public boolean copyByIdTransition(Long id) {
        return false;
    }

    @Override
    public PageInfo<HouseDTO> search(HouseDTO houseDTO, int pageNum, int pageSize) {
        return null;
    }
}