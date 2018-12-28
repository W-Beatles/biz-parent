package cn.waynechu.renting.core.repository;

import cn.waynechu.common.bean.BeanUtil;
import cn.waynechu.renting.dal.entity.House;
import cn.waynechu.renting.dal.entity.HouseExample;
import cn.waynechu.renting.dal.mapper.HouseMapper;
import cn.waynechu.webcommon.page.PageInfo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhuwei
 * @date 2018/12/28 16:57
 */
@Repository("houseRepository")
public class HouseRepository {

    @Autowired
    private HouseMapper houseMapper;

    public House getById(Long id) {
        return houseMapper.selectByPrimaryKey(id);
    }

    public boolean create(House house) {
        return houseMapper.insertSelective(house) > 0;
    }

    public boolean updateSelective(House house) {
        return houseMapper.updateByPrimaryKeySelective(house) > 0;
    }

    public boolean removeById(Long id) {
        House house = new House();
        house.setId(id);
        house.setIsDeleted(true);
        return houseMapper.updateByPrimaryKeySelective(house) > 0;
    }

    public PageInfo<House> queryByExample(House house, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        HouseExample example = BeanUtil.beanTransfer(house, HouseExample.class);
        List<House> houses = houseMapper.selectByExample(example);
        return PageInfo.of(houses);
    }
}
