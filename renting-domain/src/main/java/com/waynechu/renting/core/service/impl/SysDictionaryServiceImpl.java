package com.waynechu.renting.core.service.impl;

import cn.waynechu.webcommon.page.PageInfo;
import cn.waynechu.webcommon.util.CollectionUtil;
import com.waynechu.renting.core.convert.dto.SysDictionaryDtoConvert;
import com.waynechu.renting.core.convert.entity.SysDictionaryConvert;
import com.waynechu.renting.core.repository.SysDictionaryRepository;
import com.waynechu.renting.dal.common.entity.SysDictionary;
import com.waynechu.renting.facade.dto.SysDictionaryDTO;
import com.waynechu.renting.facade.dto.condition.SysDictionarySearchCondition;
import com.waynechu.renting.facade.service.SysDictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author zhuwei
 * @date 2019/1/18 14:11
 */
@Slf4j
@Service(version = "1.0.0")
public class SysDictionaryServiceImpl implements SysDictionaryService {

    @Autowired
    private SysDictionaryRepository sysDictionaryRepository;

    @Override
    public SysDictionaryDTO getById(Long id) {
        SysDictionaryDTO returnValue = null;

        SysDictionary sysDictionary = sysDictionaryRepository.getById(id);
        if (sysDictionary != null) {
            returnValue = SysDictionaryConvert.toSysDictionaryDTO(sysDictionary);
        }
        return returnValue;
    }

    @Override
    public boolean create(SysDictionaryDTO sysDictionaryDTO) {
        boolean returnValue = false;

        SysDictionary sysDictionary = SysDictionaryDtoConvert.toSysDictionary(sysDictionaryDTO);
        if (sysDictionary != null) {
            returnValue = sysDictionaryRepository.create(sysDictionary);
        }
        return returnValue;
    }

    @Override
    public boolean update(SysDictionaryDTO sysDictionaryDTO) {
        boolean returnValue = false;

        SysDictionary sysDictionary = SysDictionaryDtoConvert.toSysDictionary(sysDictionaryDTO);
        if (sysDictionary != null) {
            returnValue = sysDictionaryRepository.updateSelective(sysDictionary);
        }
        return returnValue;
    }

    @Override
    public boolean removeById(Long id) {
        return sysDictionaryRepository.removeById(id);
    }

    @Override
    public PageInfo<SysDictionaryDTO> search(SysDictionarySearchCondition condition) {
        PageInfo<SysDictionaryDTO> returnValue = null;
        if (condition != null) {
            PageInfo<SysDictionary> sysDictionaryPageInfo = sysDictionaryRepository.query(condition);

            if (CollectionUtil.isNotNullOrEmpty(sysDictionaryPageInfo.getList())) {
                List<SysDictionaryDTO> sysDictionaryDTOS = SysDictionaryConvert.toSysDictionaryDTOList(sysDictionaryPageInfo.getList());
                returnValue = sysDictionaryPageInfo.replace(sysDictionaryDTOS);
            }
        }
        return returnValue;
    }
}
