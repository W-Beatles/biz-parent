package cn.waynechu.renting.core.service.impl;

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

    @Override
    public HouseDTO getById(Long id) {

        return HouseConvert.convertHouseDTO(houseRepository.getById(id));
    }

    @Override
    public boolean create(HouseDTO house) {
        return false;
    }

    @Override
    public boolean update(HouseDTO houseDTO) {
        return false;
    }

    @Override
    public boolean removeById(Long id) {
        return false;
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