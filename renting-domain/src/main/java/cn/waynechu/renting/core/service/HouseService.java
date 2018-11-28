package cn.waynechu.renting.core.service;

import cn.waynechu.renting.dal.entity.House;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * @author zhuwei
 * @date 2018/11/14 16:33
 */
public interface HouseService {

    House getById(Long id);

    boolean create(House house);

    boolean update(House house);

    boolean deleteById(Long id);

    boolean copyByIdTransition(Long id);

    PageInfo<House> search(House house, Integer pageNum, Integer pageSize);
}
