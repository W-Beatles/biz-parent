package cn.waynechu.renting.facade.service;

import cn.waynechu.renting.facade.dto.HouseDTO;
import cn.waynechu.renting.facade.dto.condition.HouseSearchCondition;
import cn.waynechu.webcommon.page.PageInfo;

/**
 * @author zhuwei
 * @date 2018/11/14 16:33
 */
public interface HouseService {

    HouseDTO getById(Long id);

    boolean create(HouseDTO house);

    boolean update(HouseDTO houseDTO);

    boolean removeById(Long id);

    boolean copyByIdTransition(Long id);

    PageInfo<HouseDTO> search(HouseSearchCondition condition);
}
