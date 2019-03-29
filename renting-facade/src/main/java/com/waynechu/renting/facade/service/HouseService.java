package com.waynechu.renting.facade.service;

import cn.waynechu.webcommon.page.PageInfo;
import com.waynechu.renting.facade.dto.HouseDTO;
import com.waynechu.renting.facade.dto.condition.HouseSearchCondition;
import org.springframework.validation.annotation.Validated;

/**
 * @author zhuwei
 * @date 2018/11/14 16:33
 */
public interface HouseService {

    HouseDTO getById(Long id);

    @interface Create {
    }

    boolean create(@Validated({Create.class}) HouseDTO house);

    @interface Update {
    }

    boolean update(@Validated({Update.class}) HouseDTO houseDTO);

    boolean removeById(Long id);

    boolean copyByIdTransition(Long id);

    PageInfo<HouseDTO> search(HouseSearchCondition condition);
}
