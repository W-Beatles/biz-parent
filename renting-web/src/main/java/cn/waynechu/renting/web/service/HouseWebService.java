package cn.waynechu.renting.web.service;

import cn.waynechu.renting.facade.dto.HouseDTO;
import cn.waynechu.renting.facade.service.HouseService;
import cn.waynechu.renting.web.convert.dto.HouseDtoConvert;
import cn.waynechu.renting.web.model.ModelHouse;
import cn.waynechu.webcommon.page.PageInfo;
import cn.waynechu.webcommon.util.CollectionUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhuwei
 * @date 2018/12/29 9:53
 */
@Service
public class HouseWebService {

    @Reference(version = "1.0.0")
    private HouseService houseService;

    public ModelHouse getById(Long id) {
        ModelHouse returnValue = null;

        HouseDTO houseDTO = houseService.getById(id);
        if (houseDTO != null) {
            returnValue = HouseDtoConvert.toHouseResp(houseDTO);
        }
        return returnValue;
    }

    public boolean create(HouseDTO houseDTO) {
        return houseService.create(houseDTO);
    }

    public boolean update(HouseDTO houseDTO) {
        return houseService.update(houseDTO);
    }

    public boolean removeById(Long id) {
        return houseService.removeById(id);
    }

    public PageInfo<ModelHouse> search(HouseDTO houseDTO, Integer pageNum, Integer pageSize) {
        PageInfo<ModelHouse> returnValue = new PageInfo<>(pageNum, pageSize);

        PageInfo<HouseDTO> houseDTOPageInfo = houseService.search(houseDTO, pageNum, pageSize);
        List<HouseDTO> list = houseDTOPageInfo.getList();
        if (CollectionUtil.isNotNullOrEmpty(list)) {
            List<ModelHouse> houseVOList = HouseDtoConvert.toHouseRespList(list);
            returnValue = houseDTOPageInfo.replace(houseVOList);
        }
        return returnValue;
    }

    public boolean copyByIdTransition(Long id) {
        return houseService.copyByIdTransition(id);
    }
}
