package cn.waynechu.renting.web.service;

import cn.waynechu.renting.facade.dto.HouseDTO;
import cn.waynechu.renting.facade.dto.condition.HouseSearchCondition;
import cn.waynechu.renting.facade.service.HouseService;
import cn.waynechu.renting.web.convert.HouseDtoConvert;
import cn.waynechu.renting.web.model.ModelHouse;
import cn.waynechu.renting.web.request.HouseCreateRequest;
import cn.waynechu.renting.web.request.HouseSearchRequest;
import cn.waynechu.renting.web.request.HouseUpdateRequest;
import cn.waynechu.webcommon.page.PageInfo;
import cn.waynechu.webcommon.util.BeanUtil;
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

    public boolean create(HouseCreateRequest request) {
        HouseDTO houseDTO = BeanUtil.beanTransfer(request, HouseDTO.class);
        return houseService.create(houseDTO);
    }

    public boolean update(HouseUpdateRequest request) {
        HouseDTO houseDTO = BeanUtil.beanTransfer(request, HouseDTO.class);
        return houseService.update(houseDTO);
    }

    public boolean removeById(Long id) {
        return houseService.removeById(id);
    }

    public PageInfo<ModelHouse> search(HouseSearchRequest request) {
        HouseSearchCondition condition = BeanUtil.beanTransfer(request, HouseSearchCondition.class);
        PageInfo<HouseDTO> houseDTOPageInfo = houseService.search(condition);
        List<HouseDTO> list = houseDTOPageInfo.getList();

        List<ModelHouse> houseVOList = HouseDtoConvert.toHouseRespList(list);
        return houseDTOPageInfo.replace(houseVOList);
    }

    public boolean copyByIdTransition(Long id) {
        return houseService.copyByIdTransition(id);
    }
}
