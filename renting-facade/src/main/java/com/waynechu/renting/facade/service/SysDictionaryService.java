package com.waynechu.renting.facade.service;

import cn.waynechu.facade.common.page.PageInfo;
import com.waynechu.renting.facade.dto.SysDictionaryDTO;
import com.waynechu.renting.facade.dto.condition.SysDictionarySearchCondition;

/**
 * @author zhuwei
 * @date 2019/1/18 14:07
 */
public interface SysDictionaryService {

    SysDictionaryDTO getById(Long id);

    boolean create(SysDictionaryDTO house);

    boolean update(SysDictionaryDTO houseDTO);

    boolean removeById(Long id);

    PageInfo<SysDictionaryDTO> search(SysDictionarySearchCondition condition);
}
