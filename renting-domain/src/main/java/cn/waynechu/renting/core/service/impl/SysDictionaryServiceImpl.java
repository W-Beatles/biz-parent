package cn.waynechu.renting.core.service.impl;

import cn.waynechu.renting.core.convert.dto.SysDictionaryDtoConvert;
import cn.waynechu.renting.core.convert.entity.SysDictionaryConvert;
import cn.waynechu.renting.core.repository.SysDictionaryRepository;
import cn.waynechu.renting.dal.common.entity.SysDictionary;
import cn.waynechu.renting.facade.dto.SysDictionaryDTO;
import cn.waynechu.renting.facade.service.SysDictionaryService;
import cn.waynechu.webcommon.page.PageInfo;
import cn.waynechu.webcommon.util.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhuwei
 * @date 2019/1/18 14:11
 */
@Slf4j
@Service("sysDictionaryService")
public class SysDictionaryServiceImpl implements SysDictionaryService {

    @Autowired
    private SysDictionaryRepository sysDictionaryRepository;

//    @Cacheable(cacheNames = "dictionary", key = "#id")
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
    public PageInfo<SysDictionaryDTO> search(SysDictionaryDTO sysDictionaryDTO, int pageNum, int pageSize) {
        PageInfo<SysDictionaryDTO> returnValue = new PageInfo<>(pageNum, pageSize);

        if (sysDictionaryDTO != null) {
            SysDictionary sysDictionary = SysDictionaryDtoConvert.toSysDictionary(sysDictionaryDTO);
            PageInfo<SysDictionary> sysDictionaryPageInfo = sysDictionaryRepository.query(sysDictionary, pageNum, pageSize);

            if (CollectionUtil.isNotNullOrEmpty(sysDictionaryPageInfo.getList())) {
                List<SysDictionaryDTO> sysDictionaryDTOS = SysDictionaryConvert.toSysDictionaryDTOList(sysDictionaryPageInfo.getList());
                returnValue = sysDictionaryPageInfo.replace(sysDictionaryDTOS);
            }
        }
        return returnValue;
    }
}
