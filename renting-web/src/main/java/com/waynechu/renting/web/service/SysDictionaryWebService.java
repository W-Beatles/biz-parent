package com.waynechu.renting.web.service;

import cn.waynechu.facade.common.page.PageInfo;
import cn.waynechu.springcloud.common.util.BeanUtil;
import com.waynechu.renting.facade.dto.SysDictionaryDTO;
import com.waynechu.renting.facade.dto.condition.SysDictionarySearchCondition;
import com.waynechu.renting.facade.service.SysDictionaryService;
import com.waynechu.renting.web.convert.SysDictionaryDtoConvert;
import com.waynechu.renting.web.request.SysDictionaryCreateRequest;
import com.waynechu.renting.web.request.SysDictionarySearchRequest;
import com.waynechu.renting.web.request.SysDictionaryUpdateRequest;
import com.waynechu.renting.web.response.SysDictionaryResponse;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhuwei
 * @date 2019/1/18 14:32
 */
@Service
public class SysDictionaryWebService {

    @Reference(version = "1.0.0")
    private SysDictionaryService sysDictionaryService;

    public SysDictionaryResponse getById(Long id) {
        SysDictionaryResponse returnValue = null;

        SysDictionaryDTO sysDictionaryDTO = sysDictionaryService.getById(id);
        if (sysDictionaryDTO != null) {
            returnValue = SysDictionaryDtoConvert.toSysDictionaryResp(sysDictionaryDTO);
        }
        return returnValue;
    }

    public boolean create(SysDictionaryCreateRequest request) {
        SysDictionaryDTO sysDictionaryDTO = BeanUtil.beanTransfer(request, SysDictionaryDTO.class);
        return sysDictionaryService.create(sysDictionaryDTO);
    }

    public boolean update(SysDictionaryUpdateRequest request) {
        SysDictionaryDTO sysDictionaryDTO = BeanUtil.beanTransfer(request, SysDictionaryDTO.class);
        return sysDictionaryService.update(sysDictionaryDTO);
    }

    public boolean removeById(Long id) {
        return sysDictionaryService.removeById(id);
    }

    public PageInfo<SysDictionaryResponse> search(SysDictionarySearchRequest request) {
        SysDictionarySearchCondition condition = BeanUtil.beanTransfer(request, SysDictionarySearchCondition.class);
        PageInfo<SysDictionaryDTO> houseDTOPageInfo = sysDictionaryService.search(condition);

        List<SysDictionaryDTO> list = houseDTOPageInfo.getList();
        List<SysDictionaryResponse> houseVOList = SysDictionaryDtoConvert.toSysDictionaryRespList(list);
        return houseDTOPageInfo.replace(houseVOList);
    }
}
