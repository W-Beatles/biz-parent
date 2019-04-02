package com.waynechu.renting.web.service;

import cn.waynechu.facade.common.page.PageInfo;
import cn.waynechu.spirngcloud.common.util.BeanUtil;
import com.waynechu.renting.facade.dto.HouseDTO;
import com.waynechu.renting.facade.dto.condition.HouseSearchCondition;
import com.waynechu.renting.facade.service.HouseService;
import com.waynechu.renting.web.convert.HouseDtoConvert;
import com.waynechu.renting.web.request.HouseCreateRequest;
import com.waynechu.renting.web.request.HouseSearchRequest;
import com.waynechu.renting.web.request.HouseUpdateRequest;
import com.waynechu.renting.web.response.HouseResponse;
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

    public HouseResponse getById(Long id) {
        HouseResponse returnValue = null;

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

    public PageInfo<HouseResponse> search(HouseSearchRequest request) {
        HouseSearchCondition condition = BeanUtil.beanTransfer(request, HouseSearchCondition.class);
        PageInfo<HouseDTO> houseDTOPageInfo = houseService.search(condition);
        List<HouseDTO> list = houseDTOPageInfo.getList();

        List<HouseResponse> houseVOList = HouseDtoConvert.toHouseRespList(list);
        return houseDTOPageInfo.replace(houseVOList);
    }

    public boolean copyByIdTransition(Long id) {
        return houseService.copyByIdTransition(id);
    }
}
