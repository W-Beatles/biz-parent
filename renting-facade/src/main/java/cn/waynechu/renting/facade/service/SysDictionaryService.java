package cn.waynechu.renting.facade.service;

import cn.waynechu.renting.facade.dto.SysDictionaryDTO;
import cn.waynechu.renting.facade.dto.condition.SysDictionarySearchCondition;
import cn.waynechu.webcommon.page.PageInfo;

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
