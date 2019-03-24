package cn.waynechu.renting.web.service;

import cn.waynechu.renting.facade.dto.SysDictionaryDTO;
import cn.waynechu.renting.facade.service.SysDictionaryService;
import cn.waynechu.renting.web.convert.dto.SysDictionaryDtoConvert;
import cn.waynechu.renting.web.model.ModelSysDictionary;
import cn.waynechu.webcommon.page.PageInfo;
import cn.waynechu.webcommon.util.CollectionUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ModelSysDictionary getById(Long id) {
        ModelSysDictionary returnValue = null;

        SysDictionaryDTO sysDictionaryDTO = sysDictionaryService.getById(id);
        if (sysDictionaryDTO != null) {
            returnValue = SysDictionaryDtoConvert.toSysDictionaryResp(sysDictionaryDTO);
        }
        return returnValue;
    }

    public boolean create(SysDictionaryDTO sysDictionaryDTO) {
        return sysDictionaryService.create(sysDictionaryDTO);
    }

    public boolean update(SysDictionaryDTO sysDictionaryDTO) {
        return sysDictionaryService.update(sysDictionaryDTO);
    }

    public boolean removeById(Long id) {
        return sysDictionaryService.removeById(id);
    }

    public PageInfo<ModelSysDictionary> search(SysDictionaryDTO sysDictionaryDTO, Integer pageNum, Integer pageSize) {
        PageInfo<ModelSysDictionary> returnValue = new PageInfo<>(pageNum, pageSize);

        PageInfo<SysDictionaryDTO> houseDTOPageInfo = sysDictionaryService.search(sysDictionaryDTO, pageNum, pageSize);
        List<SysDictionaryDTO> list = houseDTOPageInfo.getList();
        if (CollectionUtil.isNotNullOrEmpty(list)) {
            List<ModelSysDictionary> houseVOList = SysDictionaryDtoConvert.toSysDictionaryRespList(list);
            returnValue = houseDTOPageInfo.replace(houseVOList);
        }
        return returnValue;
    }
}
