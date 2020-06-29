package cn.waynechu.utility.domain.service;

import cn.waynechu.utility.dal.dataobject.DictionaryTypeDO;
import cn.waynechu.utility.domain.repository.DictionaryRepository;
import cn.waynechu.utility.domain.repository.DictionaryTypeRepository;
import cn.waynechu.utility.facade.request.CreateDicTypeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhuwei
 * @date 2020-06-29 00:07
 */
@Service
public class DictionaryService {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Autowired
    private DictionaryTypeRepository dictionaryTypeRepository;

    /**
     * 添加字典类型
     *
     * @param request req
     * @return 字典类型id
     */
    public Long create(CreateDicTypeRequest request) {
        return null;
    }
}
